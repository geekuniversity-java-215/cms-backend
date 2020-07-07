package com.github.geekuniversity_java_215.cmsbackend.core.controllers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.JrpcMethodHandler;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class ReflectionUtils {

    protected final ObjectMapper objectMapper;

    public ReflectionUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    /**
     * Create wrapper around JRPC method handler
     * <br>to convert params from JsonNode to actual method params
     * @param bean
     * @param method
     * @return
     */
    public JrpcMethodHandler createJrpcHandlerExecutor(Object bean, Method method) {

        final HandlerSignature signature = getHandlerSignature(method);

        return jsonNode -> {

            JsonNode result;

            // jsonNode.size() -
            // Method that returns number of child nodes this node contains: for Array nodes, number of child elements, for Object nodes, number of fields, and for all other nodes 0.


            /*

            if (methodSig.getParams().size() == 0) {

                // execute JRPC Controller handler
                Object resultObject = method.invoke(bean);
                // convert result object to JsonNode
                result = objectMapper.valueToTree(resultObject);
            }
            //


            else if (methodSig.getParams().size() == 1 &&
                methodSig.getParams().firstEntry().getValue().getType() != List.class) {

                // get param type
                Class<?> paramType = methodSig.getParams().firstEntry().getValue().getType();
                // convert param from JsonNode to methodSig.param type
                Object paramValue =  objectMapper.treeToValue(jsonNode, paramType);

                Object resultObject = method.invoke(bean, paramValue);
                result = objectMapper.valueToTree(resultObject);
            }
            else if (methodSig.getParams().size() == 1 &&
                methodSig.getParams().firstEntry().getValue().getType() == List.class) {

                result = null;
            }
            else {
                throw new ParseException(0, "JRPC method " + method.getName() + " unknown parameter type");
            }
            */


            /*
            // Тут надо преобразовать к jsonNode к List<> и засунуть этот List<> целиком в параметр handler
            if (jsonNode.isArray()) {
                throw new NotImplementedException();
//                if( methodSig.getParams().size() != 1) {
//                    throw new ParseException(0, "JRPC method " + method.getName() + " await only one parameter");
//                }
            }
            // only 1 params present in method signature, пихаем весь jsonNode в параметр handler
            else if (jsonNode.size() == 1) {

                if( methodSig.getParams().size() != 1) {
                    throw new ParseException(0, "JRPC method " + method.getName() + " await only one parameter");
                }


            }
            // many params in method signature
            else {
                Iterator<Map.Entry<String, JsonNode>> iter = jsonNode.fields();

                if(methodSig.getParams().size() != jsonNode.size()) {
                    throw new ParseException(0, "JRPC method " + method.getName() + " wrong parameter count " +
                        " actual " + methodSig.getParams().size() + "passed" + jsonNode.size());
                }

                while (iter.hasNext()) {
                    Map.Entry<String, JsonNode> entry = iter.next();
                    String paramName = entry.getKey();

                    HandlerSignature.HandlerParameter handlerParameter  = methodSig.getParams().get(paramName);

                    // if method signature not containing jsonNode field name
                    if(handlerParameter == null) {
                        throw new ParseException(0, "JRPC method " + method.getName() + " parameter: " + paramName + " not found");
                    }

                    // get param type
                    Class<?> paramType = handlerParameter.getType();
                    // convert param from JsonNode to methodSig.param type
                    handlerParameter.setParamValue(objectMapper.treeToValue(jsonNode, paramType));
                }

                // get params
                Object[] paramValues = methodSig.getParams().values().stream().map(handlerParameter -> handlerParameter.paramValue).toArray(Object[]::new);
                // execute JRPC Controller handler
                Object resultObject = method.invoke(bean, paramValues);
                // convert result object to JsonNode
                result = objectMapper.valueToTree(resultObject);
            }
            */

            
            // get params
            Object[] paramValues = getHandlerParams(signature, jsonNode);
            // execute JRPC Controller handler
            Object resultObject = method.invoke(bean, paramValues);
            // convert result object to JsonNode
            result = objectMapper.valueToTree(resultObject);
            return result;
        };
    }





    // Create HandlerSignature
    private HandlerSignature getHandlerSignature(Method method) {

        HandlerSignature result = new HandlerSignature();
        result.setReturnType(method.getReturnType());
        result.setName(method.getName());

        // p.getParameterizedType().getTypeName()

        int position = 0;
        for (Parameter p : method.getParameters()) {

            Class<?> parameterizedClazz = null;
            if(p.getParameterizedType() instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType)p.getParameterizedType();
                parameterizedClazz = TypeFactory.rawClass(pt.getActualTypeArguments()[0]);
            }

            HandlerSignature.HandlerParameter handlerParameter =
                new HandlerSignature.HandlerParameter(p.getName(), p.getType(), parameterizedClazz, position);

            // index by parameter name
            result.getParams().put(p.getName(), handlerParameter);
            // index by parameter position
            //result.getParamsIndex().add(handlerParameter);

            position++;
        }
        return result;
    }





    private Object[] getHandlerParams(HandlerSignature signature, JsonNode jsonNode) throws JsonProcessingException {

        Object[] result = null;

        // no params
//        if (signature.getParams().size() == 0) {
//            result = null;
//        }
        // one param - copy whole jsonNode to handler parameter
        if (signature.getParams().size() == 1) {

            HandlerSignature.HandlerParameter parameter = signature.getParams().firstEntry().getValue();
            setParameterValue(parameter, jsonNode);
        }
        // many params - extract jsonNode fields to match handler params
        else {


            Iterator<Map.Entry<String, JsonNode>> it = jsonNode.fields();

            for (Map.Entry<String, HandlerSignature.HandlerParameter> entry : signature.getParams().entrySet()) {

                Map.Entry<String, JsonNode> jsonEntry = it.next(); // used for field access by index

                HandlerSignature.HandlerParameter parameter = entry.getValue();

                JsonNode paramNode = jsonNode.get(parameter.getName()); // get by name
                if(paramNode == null) { // else get by index
                    paramNode = jsonEntry.getValue();
                }

                if(paramNode == null) {
                    throw new ParseException(0, "JRPC method " + signature.getName() +
                        " parameter: " + parameter.getName() + " not found");
                }

                setParameterValue(parameter, paramNode);
            }
        }

        result = signature.getParams().values().stream()
            .map(handlerParameter -> handlerParameter.paramValue).toArray(Object[]::new);

        return result;

    }


    private void setParameterValue(HandlerSignature.HandlerParameter parameter, JsonNode paramNode) throws JsonProcessingException {

        // no generics container
        if (parameter.getParameterizedType() == null) {
            Object paramValue = objectMapper.treeToValue(paramNode, parameter.getType());
            parameter.setParamValue(paramValue);
        }
        // List<?>
        // only List generic container available now
        else {
            //noinspection rawtypes
            Class tArrayClass = Array.newInstance(parameter.getParameterizedType(), 0).getClass();

            /*//noinspection unchecked */
            //parameter.setParamValue(objectMapper.treeToValue(paramNode, tArrayClass));
            //noinspection unchecked
            List<?> paramValue = Arrays.asList((Object[])objectMapper.treeToValue(paramNode, tArrayClass));
            //noinspection unchecked

            //Object[] array = (Object[])objectMapper.treeToValue(paramNode, tArrayClass);
            //List<?> paramValue2 = Arrays.asList(array);
            //System.out.println(paramValue2);


            //noinspection unchecked
            //List<Object> paramValue3 = Collections.singletonList(objectMapper.treeToValue(paramNode, Double[].class));

            //System.out.println(paramValue);
            //System.out.println(paramValue2);
            

            //Object paramValue = Collections.singletonList(objectMapper.treeToValue(paramNode, tArrayClass));
            parameter.setParamValue(paramValue);
        }
    }
}
