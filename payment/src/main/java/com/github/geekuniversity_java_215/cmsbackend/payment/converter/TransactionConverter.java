package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.dto.TransactionDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
@Slf4j
public class TransactionConverter extends AbstractConverter<CashFlow, TransactionDto, Void> {

    @Autowired
    public TransactionConverter(TransactionMapper transactionMapper, ObjectMapper mapper) {
        this.entityMapper = transactionMapper;

        this.entityClass = CashFlow.class;
        this.dtoClass = TransactionDto.class;
        this.specClass = Void.class;
    }

      public String[] parseParams(JsonNode params, Integer countParams) {
        String[] result;

        try {
            result = objectMapper.treeToValue(params, String[].class);
            // validate
            if (result == null || result.length != countParams) {
                throw new ValidationException("Wrong arguments count");
            }

            for (int i = 0; i < countParams; i++) {
                if (result[i] == null ) {
                    throw new ValidationException(i + "st argument validation failed");
                }
            }
        } catch (JsonProcessingException e) {
            throw new ParseException(0, "params parse error", e);
        }
        return result;
    }

    @Override
    protected void validate(CashFlow cashFlow) {
        super.validate(cashFlow);

        // ... custom validation
    }

}
