package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class SchedulingConfiguration implements SchedulingConfigurer {

    // By default all @Scheduled methods share a single thread (of same TaskPool).
    // https://stackoverflow.com/questions/29796651/what-is-the-default-scheduler-pool-size-in-spring-boot
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        final CustomizableThreadFactory threadFactory = new CustomizableThreadFactory();
        threadFactory.setDaemon(true);
        threadFactory.setThreadNamePrefix("SchedulingPool-");

        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadFactory(threadFactory);
        taskScheduler.setPoolSize(10);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);

        //ToDo: прикрутить timeout или юзать net.tascalate.concurrent
    }

    // Use this in scheduling tasks:
    //
    // Augmented CompletableFuture
    // https://github.com/vsilaev/tascalate-concurrent
    // https://mvnrepository.com/artifact/net.tascalate.concurrent/net.tascalate.concurrent.lib/0.7.1
    //
    // For java8, contains:
    // .supplyAsync( () -> ... , myExecutor).orTimeout(Duration.ofSeconds(...));
    // That WILL INTERRUPT (unlike vanilla CompletableFuture in 9) thread that hang on I/O on timeout.
    // Friendly new ThreadPoolExecutor(...) wrapper (named constructor args)

}