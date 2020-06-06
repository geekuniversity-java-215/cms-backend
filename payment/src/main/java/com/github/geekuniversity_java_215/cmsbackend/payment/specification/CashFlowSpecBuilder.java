package com.github.geekuniversity_java_215.cmsbackend.payment.specification;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("ConstantConditions")
@Slf4j
public class CashFlowSpecBuilder {

    public static Specification<CashFlow> build (CashFlowSpecDto cashFlowSpecDto){
        if (cashFlowSpecDto==null){
            return null;
        }
        Specification<CashFlow> specOut=Specification.where(null);
        CashFlowSpecDto s=cashFlowSpecDto;

        final String dateCreate="create";
        final String userId="id";

        if (s.getStartDate()!=null && s.getEndDate()!=null){
            specOut=specOut
                    .and((root, query, builder) ->builder.greaterThanOrEqualTo(root.get(dateCreate), s.getStartDate()))
                    .and((root, query, builder) ->builder.lessThanOrEqualTo(root.get(dateCreate), s.getEndDate()))
                    .and((root, query, builder) ->builder.lessThanOrEqualTo(root.get(userId), s.getUser_id()));
        }

        return specOut;
    }

}
