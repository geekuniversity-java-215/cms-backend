package com.github.geekuniversity_java_215.cmsbackend.core.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.data.HttpResponseFactory;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.utils.HandlerSignature;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.utils.ReflectionUtils;
import com.github.geekuniversity_java_215.cmsbackend.core.exceptions.InvalidLogicException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.JrpcException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.request.JrpcRequestHeader;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.protocol.response.JrpcErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethodHandler;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.data.HttpResponse;



@RestController
@Slf4j
public class ApiController {

    private final static String API_VERSION = "1.0";
    private final static String API_PATH = "/api/" + API_VERSION + "/";

    private final ApplicationContext context;
    private final ObjectMapper objectMapper;
    private final ReflectionUtils reflectionUtils;



    // Map обработчиков jrpc запросов                           // ConcurrentHashMap
    private final Map<String, JrpcMethodHandler> handlers = new HashMap<>();

    // Список параметров обработчиков jrpc запроса
    private final Map<String, HandlerSignature> handlersParams = new HashMap<>();

    @Autowired
    public ApiController(ApplicationContext context, ObjectMapper objectMapper, ReflectionUtils reflectionUtils) {
        this.context = context;
        this.objectMapper = objectMapper;
        this.reflectionUtils = reflectionUtils;
    }

    @PostConstruct
    private void postConstruct() {
        // scanning/loading handler beans
        loadHandlers();
    }


    @RequestMapping(value = API_PATH, method = RequestMethod.POST)
    public ResponseEntity<?> processRequest(@RequestBody String request) {

        // id ответа
        Long id = null;

        // http response
        HttpResponse httpResponse;

        log.debug("POST " + API_PATH + ": " + request);


        try {

            // JrpcRequestHeader не содержит свойство params, поэтому десереализация происходит без проблем
            // вытаскиваем все поля, кроме params
            JrpcRequestHeader jsonRpcHeader = objectMapper.readValue(request, JrpcRequestHeader.class);

            id = jsonRpcHeader.getId();
            String version = jsonRpcHeader.getVersion();
            final String method = jsonRpcHeader.getMethod();

            // Parsing jrpc header ------------------------------------------------------------------

            // Не поддерживаемая версия jrpc
            if (StringUtils.isBlank(version) || !version.equals("2.0")) {
                throw new JrpcException("JRPC version not supported", JrpcErrorCode.INVALID_REQUEST);
            }
            // Мы не поддерживаем уведомления для сервера
            if (id == null) {
                throw new JrpcException("JRPC has no id specified", JrpcErrorCode.INVALID_REQUEST);
            }
            // метод не указан
            if (StringUtils.isBlank(method)  ) {
                throw new JrpcException("No JRPC method specified", JrpcErrorCode.METHOD_NOT_FOUND);
            }
            // метод не найден
            if (!handlers.containsKey(method)) {
                throw new JrpcException("JRPC method not found", JrpcErrorCode.METHOD_NOT_FOUND);
            }

            // ------------------------------------------------------------------------------


            // reading params
            JsonNode params = objectMapper.readTree(request).get("params");

            // getting handler
            JrpcMethodHandler handler = handlers.get(method);


            // executing RPC
            JsonNode json;
            try {
                json = handler.apply(params);
            }
            // вышвыриваем наверх
            catch (InvocationTargetException e) {

                Throwable innerEx = e.getCause();
                Assert.notNull(innerEx, "InvocationTargetException is null");
                throw innerEx;
            }

            // encapsulate result to http
            httpResponse = HttpResponseFactory.getOk(json);

        }
        // Access denied
        catch (AccessDeniedException e) {
            log.error("Forbidden",e);
            // тут jrpc не инкапсулируется
            httpResponse = HttpResponseFactory.getForbidden();
        }
        // jrpc call error (method not found, etc)
        catch (JrpcException e) {
            log.error("JrpcError",e);
            httpResponse = HttpResponseFactory.getError(e);
        }

        // invalid request json
        catch (JsonProcessingException e) {
            log.error("Bad request json: " + e.getMessage(), e);
            httpResponse = HttpResponseFactory.getError(HttpStatus.BAD_REQUEST, e);
        }
        // invalid request params json(param parse error, wrong type, etc)
        catch (ParseException e) {
            log.error("Json param parse error: " + e.getMessage(), e);
            JrpcException ex = new JrpcException("Json param parse error", JrpcErrorCode.INVALID_PARAMS, e);
            httpResponse = HttpResponseFactory.getError(ex);
        }
        // params not passed validation
        catch (ConstraintViolationException e) {
            String message = "Param validation violation: " + e.getConstraintViolations().toString();
            log.error(message, e);
            JrpcException ex = new JrpcException(message, JrpcErrorCode.INVALID_PARAMS, e);
            httpResponse = HttpResponseFactory.getError(ex);

        }
        // hand-made param validation
        catch (ValidationException e) {
            log.error("Param validation violation: " + e.getMessage(), e);
            JrpcException ex = new JrpcException("Param validation violation", JrpcErrorCode.INVALID_PARAMS, e);
            httpResponse = HttpResponseFactory.getError(ex);
        }
        // hand-made param check
        catch (IllegalArgumentException e) {
            log.error("Illegal param: " + e.getMessage(), e);
            JrpcException ex = new JrpcException("Illegal param", JrpcErrorCode.INVALID_PARAMS, e);
            httpResponse = HttpResponseFactory.getError(ex);
        }

        // params logic violation
        catch (InvalidLogicException e) {
            log.error("Logic violation: " + e.getMessage(), e);
            JrpcException ex = new JrpcException("Logic violation", JrpcErrorCode.INVALID_PARAMS, e);
            httpResponse = HttpResponseFactory.getError(ex);
        }

        catch (Throwable e) {
            log.error("Internal resource-server error in controller: " + e.getMessage(), e);
            httpResponse = HttpResponseFactory.getError(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        // не должно быть такого
        Assert.notNull(httpResponse.getResult(), "httpResponse.getResult() == null");

        // add request id to response (if have one)
        httpResponse.getResult().setId(id);

        log.debug(httpResponse.getResult().toString());

        return new ResponseEntity<>(httpResponse.getResult(), httpResponse.getStatus());
    }



    // =================================================================================================
    // Reflection manipulations
    // =================================================================================================

    /**
     * Fill handlers based on beans annotated with @JrpcController
     * <br>
     * Using reflection to get all classes and theirs methods that handle clients requests
     */
    private void loadHandlers() {

        try {

            // Ask spring to find (and load if not loaded?) all beans annotated with @JrpcController
            Map<String,Object> beans = context.getBeansWithAnnotation(JrpcController.class);

            // https://stackoverflow.com/questions/27929965/find-method-level-custom-annotation-in-a-spring-context
            for (Map.Entry<String, Object> entry : beans.entrySet()) {

                Object bean = entry.getValue();
                Class<?> beanClass = bean.getClass();
                JrpcController jrpcController = beanClass.getAnnotation(JrpcController.class);

                // bean is an AOP proxy
                if (jrpcController == null) {
                    beanClass = AopProxyUtils.ultimateTargetClass(bean);
                    jrpcController = beanClass.getAnnotation(JrpcController.class);
                }
                
                // Ищем в бине метод, помеченный аннотацией @JrpcMethod
                for (Method method : beanClass.getDeclaredMethods()) {

                    if (method.isAnnotationPresent(JrpcMethod.class)) {
                        //Should give you expected results
                        JrpcMethod jrpcMethod = method.getAnnotation(JrpcMethod.class);

                        // Checked(как и unchecked) исключения будут проброшены к вызывающему
                        //JrpcMethodHandler handler = params -> (JsonNode)method.invoke(bean, params);
                        JrpcMethodHandler handler = reflectionUtils.createJrpcHandlerExecutor(bean, method);

                        String controllerMethodName = jrpcController.value() + "." + jrpcMethod.value();

                        // check that name is unique
                        if(handlers.containsKey(controllerMethodName)) {
                            throw new IllegalArgumentException("jrpcController.jrpcMethod: " +
                                controllerMethodName + " already exists");
                        }
                        
                        handlers.put(controllerMethodName, handler);
                        //handlersParams.put(controllerMethodName, getHandlerParams(method));
                    }
                }
            }

        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

    }
}
