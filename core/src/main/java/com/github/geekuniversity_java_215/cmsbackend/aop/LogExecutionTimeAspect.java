package com.github.geekuniversity_java_215.cmsbackend.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Aspect
@Component
public class LogExecutionTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Around("@annotation(com.github.geekuniversity_java_215.cmsbackend.aop.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final long start = System.currentTimeMillis();

        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - start;

        log.debug("{} executed in  + executionTime {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

}