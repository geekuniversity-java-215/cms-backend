package com.github.geekuniversity_java_215.cmsbackend.services.impl;

import net.tascalate.concurrent.CompletableTask;
import net.tascalate.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Consumer;

@Component
public class JobPool<T> {
    private ThreadPoolExecutor threadPool;
    private Duration timeout;


    // On job done handler
    private Consumer<T> callback;

    public JobPool(Integer poolSize, Duration timeout, Consumer<T> callback) {

        this.timeout = timeout;


        if (poolSize == null || poolSize <= 0) {
            poolSize = 1;
        }

        if (callback == null) {
            throw new IllegalArgumentException("callback == null");
        }
        //   this.callback = callback;

        final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setDaemon(true);
        threadFactory.setThreadNamePrefix("JobPool-");


        threadPool = new ThreadPoolTaskExecutor(
                poolSize, poolSize,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(poolSize),
                threadFactory);

    }


    public void addRunnable(Runnable runnable, Executor executor) {
        CompletableTask
                .runAsync(runnable, executor)

                .orTimeout(timeout)
                .handleAsync((t, throwable) -> {

                    System.out.println("handle: + " + Thread.currentThread().getName());

                    if (throwable != null) {
                        System.out.println("Error:  " + throwable);
                        t = null;
                    }
                    System.out.println("handle:" + t);
                    return t;
                })
                // Оповещаем о завершении задачи
                .thenAcceptAsync(
                        t -> {
                            System.out.println("callback: + " + Thread.currentThread().getName());
                            callback.accept((T) t);
                        }
                )
                // обрабатываем ошибки, возникшие в ходе оповещения о завершении задачи

                .handleAsync((aVoid, throwable) -> {

                    if (throwable != null) {
                        // Ловим ошибки в вызове callback.accept(t)
                        System.out.println("УПАЛО в callback(): " + throwable);
                    }
                    return null;
                });


    }


    public void shutdown() throws InterruptedException {

        System.out.println("Awaiting termination ...");
        threadPool.shutdown();
        threadPool.awaitTermination(10000, TimeUnit.MILLISECONDS);
    }


//    public static class TestJobPool {
//
//        public static void doTest() {
//
//            JobPool<Integer> jobPool =
//                    new JobPool<>(3, Duration.ofSeconds(5),
//                            s -> System.out.println("Результат: " + s));
//
//
//            jobPool.add(() -> 1 + 1);
//            jobPool.add(() -> 2 + 2);
//            jobPool.add(unchecked(() -> {TimeUnit.SECONDS.sleep(10); return 3 + 3;}));
//        }
//    }


}
