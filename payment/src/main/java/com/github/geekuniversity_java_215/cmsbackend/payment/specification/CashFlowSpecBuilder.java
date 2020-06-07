package com.github.geekuniversity_java_215.cmsbackend.payment.specification;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowSpecDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("ConstantConditions")
@Slf4j
public class CashFlowSpecBuilder {

    public static Specification<CashFlow> build (CashFlowSpecDto cashFlowSpecDto){

        if (cashFlowSpecDto == null){
            return null;
        }

        Specification<CashFlow> specA = Specification.where(null);
        CashFlowSpecDto s = cashFlowSpecDto;

        final String created = "created";
        final String idName = "id";
        final String user = "user";
        final String dateSuccess = "dateSuccess";



        // BETWEEN CREATE DATES
        if (s.getStartDate() != null && s.getEndDate() != null) {

            specA = specA.and(
                (root, query, builder) -> builder.between(root.get(created), s.getStartDate(), s.getEndDate()));
        }

        // CREATED LESS THAN END_DATE
        if (s.getStartDate() == null && s.getEndDate() != null) {

            specA = specA.and(
                (root, query, builder) -> builder.lessThanOrEqualTo(root.get(created), s.getEndDate()));
        }

        // CREATED GREATER THAN START_DATE
        if (s.getStartDate() != null && s.getEndDate() == null) {

            specA = specA.and(
                (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(created), s.getStartDate()));
        }


        // FILTER BY USER
        if (s.getUser() != null) {

            specA = specA.and(
                (root, query, builder) -> builder.equal(root.get(user).get(idName), s.getUser().getId()));
        }

        // IS SUCCESSFUL
        if (s.getSuccessful() != null) {

            if (s.getSuccessful()) {
                specA = specA.and((root, query, builder) -> builder.isNotNull(root.get(dateSuccess)));
            }
            else {
                specA = specA.and((root, query, builder) -> builder.isNull(root.get(dateSuccess)));
            }
        }

        return specA;
    }

}
