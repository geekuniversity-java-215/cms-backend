package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.payment.dto.TransactionDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;

@Component
public class TransactionConverter extends AbstractConverter<Transaction, TransactionDto, Void> {

    @Autowired
    public TransactionConverter(TransactionMapper transactionMapper) {
        this.entityMapper = transactionMapper;

        this.entityClass = Transaction.class;
        this.dtoClass = TransactionDto.class;
        this.specClass = Void.class;
    }
    // Json => Long
    public Long getAmount(JsonNode params) {

        Long result;

        // parsing request
        try {
            result = objectMapper.treeToValue(params, Long.class);

            // validate
            if (result == null || result < 0) {
                throw new ValidationException("Id validation failed");
            }
        }
        catch (JsonProcessingException e) {
            throw new ParseException(0, "Id parse error", e);
        }
        return result;
    }

    @Override
    protected void validate(Transaction transaction) {
        super.validate(transaction);

        // ... custom validation
    }
}
