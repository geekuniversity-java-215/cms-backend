package com.github.geekuniversity_java_215.cmsbackend.core.converters.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import javax.validation.Validator;
import java.math.BigDecimal;

@Component
public class PaymentConverter {

    private Validator validator;
    protected ObjectMapper objectMapper;

    // Json => Dto
    public BigDecimal getAmount(JsonNode params) {

        BigDecimal result;

        try {
            result = objectMapper.treeToValue(params, BigDecimal.class);

            // validate
            if (result == null || result.compareTo(BigDecimal.ZERO) < 0) {
                throw new ValidationException("Wrong arguments");
            }
        } catch (JsonProcessingException e) {
            throw new ParseException(0, "params parse error", e);
        }
        return result;
    }

    // Dto => Json
    public JsonNode toJson(Object o) {
        try {
            return objectMapper.valueToTree(o);
        }
        catch (Exception e) {
            throw new ParseException(0, "toJson convert error", e);
        }
    }
}
