package com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Mark class method as jrpc request handler
 * <br>
 * Позволяет различать методы класса
 */

@Retention(RetentionPolicy.RUNTIME)


@Target(ElementType.METHOD)
public @interface JrpcMethod {
    /**
     * Method name, coming from json request 'method' param value
     */
    String value();
}
