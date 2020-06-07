package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractConverter;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CashFlowConverter extends AbstractConverter<CashFlow, CashFlowDto, CashFlowSpecDto> {

    @Autowired
    public CashFlowConverter(CashFlowMapper transactionMapper, ObjectMapper mapper) {
        this.entityMapper = transactionMapper;

        this.entityClass = CashFlow.class;
        this.dtoClass = CashFlowDto.class;
        this.specClass = CashFlowSpecDto.class;
    }

//    public String[] doubleParams(JsonNode params) {
//
//      public String[] doubleParams(JsonNode params) {
//        String[] result;
//
//        try {
//            result = objectMapper.treeToValue(params, String[].class);
//            // validate
//            if (result == null || result.length != 2) {
//                throw new ValidationException("Wrong arguments count");
//            }
//
//            for (int i = 0; i < 2; i++) {
//                if (result[i] == null ) {
//                    throw new ValidationException(i + "st argument validation failed");
//                }
//            }
//        } catch (JsonProcessingException e) {
//            throw new ParseException(0, "params parse error", e);
//        }
//        return result;
//    }

    @Override
    protected void validate(CashFlow cashFlow) {
        super.validate(cashFlow);

        // ... custom validation
    }

}
