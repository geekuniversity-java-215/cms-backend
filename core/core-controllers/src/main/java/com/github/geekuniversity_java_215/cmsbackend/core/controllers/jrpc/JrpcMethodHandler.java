package com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.pivovarit.function.ThrowingFunction;

/**
 * Функциональный интерфейс обработчика jrpc запроса
 * alias
 */
public interface JrpcMethodHandler extends ThrowingFunction<JsonNode,JsonNode, Exception> {}