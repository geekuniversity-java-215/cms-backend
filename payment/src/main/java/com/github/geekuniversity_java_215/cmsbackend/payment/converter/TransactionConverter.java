package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.dto.TransactionDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
@Slf4j
public class TransactionConverter extends AbstractConverter<Transaction, TransactionDto, Void> {
    private final TransactionMapper transactionMapper;
    private ObjectMapper mapper;

    @Autowired
    public TransactionConverter(TransactionMapper transactionMapper, ObjectMapper mapper) {
        this.transactionMapper = transactionMapper;

        this.entityClass = Transaction.class;
        this.dtoClass = TransactionDto.class;
        this.specClass = Void.class;
        this.mapper=mapper;
    }

      public String[] parseDouble(JsonNode params) {
        String[] result;

        try {
            result = objectMapper.treeToValue(params, String[].class);
            // validate
            if (result == null || result.length != 2) {
                throw new ValidationException("Wrong arguments count");
            }

            for (int i = 0; i < 2; i++) {
                if (result[i] == null ) {
                    throw new ValidationException(i + "st argument validation failed");
                }
            }
        } catch (JsonProcessingException e) {
            throw new ParseException(0, "params parse error", e);
        }
        return result;
    }

    // Entity => JsonNode
    public JsonNode toJson(Transaction transaction){  //(Object o) {
        log.info("Entity => Json");

        TransactionDto transactionDto;
        transactionDto= transactionMapper.toDto(transaction);
        try {
            return transactionDto ;//objectMapper.valueToTree(o);
        }
        catch (Exception e) {
            throw new ParseException(0, "toJson convert error", e);
        }
    }


}
