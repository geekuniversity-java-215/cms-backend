package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.controllers.calculator;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.services.CalculatorService;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcController;
import com.github.geekuniversity_java_215.cmsbackend.core.controllers.jrpc.annotations.JrpcMethod;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.HandlerName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Интерфейс управления калькулятором
 */
@JrpcController(HandlerName.calculator.path)
public class CalculatorController {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CalculatorService calculatorService;
    private final ObjectMapper objectMapper;


    @Autowired
    protected CalculatorController(CalculatorService calculatorService, ObjectMapper objectMapper) {
        this.calculatorService = calculatorService;
        this.objectMapper = objectMapper;
    }



    // Далее для простоты опущено использование класса Converter,
    // (имеющий свою реализацию под каждую отдельную entity)
    // который использует MapStruct и выполняет преобразование JSON <-> DTO <-> Entity
    // + проводит валидацию входных данных


    /**
     * Add
     */
    @JrpcMethod(HandlerName.calculator.add)
    public double add(double a, double b) {

        return calculatorService.add(a, b);
    }


    /**
     * Substract
     */
    @JrpcMethod(HandlerName.calculator.sub)
    public Double sub(Double a, Double b) {

        return calculatorService.sub(a, b);
    }


    /**
     * Multiply
     */
    @JrpcMethod(HandlerName.calculator.mul)
    public Double mul(Double a, Double b) {

        return calculatorService.mul(a, b);
    }


    /**
     * Divide
     */
    @JrpcMethod(HandlerName.calculator.div)
    public Double div(Double a, Double b) {
        
        return calculatorService.div(a, b);
    }



    @JrpcMethod(HandlerName.calculator.ssuper)
    public List<Double> ssuper(List<Double> list) {

        double l0 = 34;
        double l1 = list.get(0) + list.get(1);
        return Stream.of(l0, l1).collect(Collectors.toList());
    }


    @JrpcMethod(HandlerName.calculator.zoper)
    public List<Double> zoper(double l0, List<Double> list) {

        double l1 = list.get(0) + list.get(1);
        return Stream.of(l0, l1).collect(Collectors.toList());
    }

     /*


    // ====================================================================================

    

    // Json => Entity
    private Double[] parseDoublePair(JsonNode params) {

        //List<Double> result;
        //JavaType jt = objectMapper.getTypeFactory().constructCollectionType(List.class, Double.class);
        //result = objectMapper.readValue(objectMapper.treeAsTokens(params), jt);

        Double[] result;

        try {

            result = objectMapper.treeToValue(params, Double[].class);

            // validate
            if (result == null || result.length != 2) {
                throw new ValidationException("Wrong arguments count");
            }

            for (int i = 0; i < 2; i++) {
                if (result[i] == null || result[i].isNaN() || result[i].isInfinite()) {
                    throw new ValidationException(i + "st argument validation failed");
                }
            }

        } catch (JsonProcessingException e) {
            throw new ParseException(0, "params parse error", e);
        }

        return result;
    }



    // Entity => Json
    private JsonNode toJson(Object o) {
        try {
            return objectMapper.valueToTree(o);
        }
        catch (Exception e) {
            throw new ParseException(0, "toJson convert error", e);
        }
    }

    */


}
