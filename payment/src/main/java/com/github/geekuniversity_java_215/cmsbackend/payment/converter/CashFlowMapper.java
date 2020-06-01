package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.address.AddressMapper;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.payment.CashFlowDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.services.CashFlowService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {InstantMapper.class, AddressMapper.class})
public abstract class CashFlowMapper extends AbstractMapper<CashFlow, CashFlowDto> {

    @Autowired
    CashFlowService cashFlowService;


    @PostConstruct
    private void postConstruct() {
        super.setBaseRepoAccessService(cashFlowService);
        constructor = new CashFlowMapper.EntityConstructor();
    }


    public abstract CashFlowDto toDto(CashFlow cashFlow);

//    @Mapping(target = "typeOperation", ignore = true)
//    @Mapping(target = "currencyCodeType", ignore=true)
//    @Mapping(target = "payPalEmail", ignore=true)
//    @Mapping(target="dateCreate", ignore=true)
//    @Mapping(target = "dateSuccess", ignore=true)

    @Mapping(target = "user.client", ignore = true)
    @Mapping(target = "user.courier", ignore = true)
    @Mapping(target = "user.refreshTokenList", ignore = true)
    public abstract CashFlow toEntity(CashFlowDto transactionDto);

    /**
     * Custom conversion logic, need to further setup
     *
     * @param source Dto
     * @param target Entity
     */
    @AfterMapping
    CashFlow afterMapping(CashFlowDto source, @MappingTarget CashFlow target) {
        return merge(source, target);
    }

    @Component
    protected class EntityConstructor extends Constructor<CashFlow, CashFlowDto> {

        @Override
        public CashFlow create(CashFlowDto dto, CashFlow entity) {

            // Mapstruct 1.4 maybe will support constructors with params
            return new CashFlow(
                    entity.getUser(),
                    dto.getTypeOperation(),
                    dto.getAmount(),
                    dto.getPayPalEmail(),
                    dto.getCurrencyCodeType());

        }
    }
}
