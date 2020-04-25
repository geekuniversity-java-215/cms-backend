package com.github.geekuniversity_java_215.cmsbackend.utils;

import lombok.extern.slf4j.Slf4j;
import net.tascalate.concurrent.CompletableTask;
import net.tascalate.concurrent.Promise;
import net.tascalate.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.time.Duration;
import java.util.concurrent.CompletionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Slf4j
public class JobPool<T> {

    private final ThreadPoolExecutor threadPool;
    private final Duration timeout;
    private final String poolName;


    // On job done handler
    private Consumer<T> callback;

    public JobPool(String poolName, Integer poolSize, Duration timeout, Consumer<T> callback) {

        this.timeout = timeout;
        this.callback = callback;
        this.poolName = poolName;

        if (poolSize == null || poolSize <= 0) {
            poolSize = 1;
        }

        final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setDaemon(true);
        threadFactory.setThreadNamePrefix(poolName + "-");

        int startingPoolSize = poolSize / 2;
        startingPoolSize = startingPoolSize > 0 ? startingPoolSize : 1;


        threadPool = new ThreadPoolTaskExecutor(
            startingPoolSize, poolSize,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(poolSize * 2),
            threadFactory);

    }


    public Promise add(Runnable runnable) {


        return CompletableTask
            .runAsync(runnable, threadPool)
            // выставляем timeout
            .orTimeout(timeout)
            // обрабатываем ошибки возникшие во время выполнения (в том числе timeout)
            .handleAsync((t, throwable) -> {

                log.trace(poolName + " handle");

                if (throwable != null) {
                    log.error(poolName + " error: " + throwable);
                    throw new CompletionException(throwable);
                }

                log.trace(poolName + " handle result: " + t);

                // Оповещаем о завершении задачи
                if (callback != null) {
                    log.trace(poolName + " callback");
                    callback.accept(null);
                }
                return t;
            });
//            // Оповещаем о завершении задачи
//            .thenAcceptAsync(
//                t -> {
//                    if (callback != null) {
//                        log.trace(poolName + " callback");
//                        callback.accept(null);
//                    }
//                }
//            );
//            // обрабатываем ошибки, возникшие в ходе оповещения о завершении задачи
//            .handleAsync((aVoid, throwable) -> {
//                if (throwable != null) {
//                    // Ловим ошибки в вызове callback.accept(t)
//                    log.error(poolName + " error: failed in callback():" + throwable);
//                }
//                return null;
//            });
    }


    public Promise<T> add(Supplier<T> supplier) {

        return CompletableTask
            .supplyAsync(supplier, threadPool)
            // выставляем timeout
            .orTimeout(timeout)
            // обрабатываем ошибки возникшие во время выполнения (в том числе timeout)
            .handleAsync((t, throwable) -> {

                log.trace(poolName + " handle");

                if (throwable != null) {
                    log.error(poolName + " error:" + throwable);
                    throw new CompletionException(throwable);
                }

                log.trace(poolName + " handle result: " + t);

                // Оповещаем о завершении задачи
                if (callback != null) {
                    log.trace(poolName + " callback");
                    callback.accept(t);
                }
                return t;
            });
//            .thenApply(
//                t -> {
//                    if (callback != null) {
//                        log.trace(poolName + " callback");
//                        callback.accept(t);
//                    }
//                    return t;
//                }
//            );

//            // Оповещаем о завершении задачи
//            .thenAcceptAsync(
//                t -> {
//                    if (callback != null) {
//                        log.trace(poolName + " callback");
//                        callback.accept(t);
//                    }
//                }
//            );

//            // обрабатываем ошибки, возникшие в ходе оповещения о завершении задачи
//            .handleAsync((t, throwable) -> {
//                if (throwable != null) {
//                    // Ловим ошибки в вызове callback.accept(t)
//                    log.error(poolName + " error: failed in callback():" + throwable);
//                }
//                return t;
//            });
    }

    public void shutdown() throws InterruptedException {

        System.out.println("Awaiting termination ...");
        threadPool.shutdown();
        threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
    }

    public String getPoolName() {
        return poolName;
    }

}
