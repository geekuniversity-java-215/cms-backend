package com.github.geekuniversity_java_215.cmsbackend.core.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.protocol.http.HttpResponse;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.JrpcException;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.request.JrpcRequestHeader;
import com.github.geekuniversity_java_215.cmsbackend.protocol.jrpc.response.JrpcErrorCode;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.jrpc.JrpcMethodHandler;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.geekuniversity_java_215.cmsbackend.utils.controllers.HttpResponseFactory;


@RestController
@Slf4j
public class ApiController {

    private final static String API_VERSION = "1.0";
    private final static String API_PATH = "/api/" + API_VERSION + "/";

    private final ApplicationContext context;
    private final ObjectMapper objectMapper;



    // Map обработчиков jrpc запросов
    private final Map<String, JrpcMethodHandler> handlers = new ConcurrentHashMap<>();

    @Autowired
    public ApiController(ApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
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

        log.info("POST " + API_PATH + ": " + request);


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

        catch (Throwable e) {
            log.error("Internal resource-server error in controller: " + e.getMessage(), e);
            httpResponse = HttpResponseFactory.getError(HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        // не должно быть такого
        Assert.notNull(httpResponse.getResult(), "httpResponse.getResult() == null");

        // add request id to response (if have one)
        httpResponse.getResult().setId(id);

        log.info(httpResponse.getResult().toString());

        return new ResponseEntity<>(httpResponse.getResult(), httpResponse.getStatus());
    }



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
                        JrpcMethodHandler handler = params -> (JsonNode)method.invoke(bean,params);

                        handlers.put(jrpcController.value() + "." + jrpcMethod.value(), handler);
                    }
                }
            }

        } catch (BeansException e) {
            throw new RuntimeException(e);
        }

    }

}
