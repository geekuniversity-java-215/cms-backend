package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.address.AddressMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.payment.dto.TransactionDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {InstantMapper.class, AddressMapper.class})
public abstract class TransactionMapper extends AbstractMapper<Transaction, TransactionDto> {

    @Autowired
    UserService userService;

    @Mapping(source = "typeOperation", target = "typeOperation")
    @Mapping(source = "currencyCodeType", target="currencyCodeType" )
    @Mapping(source="dateCreate",target="dateCreate")
    @Mapping(source = "dateSuccess",target="dateSuccess")
    public abstract TransactionDto toDto(Transaction transaction);

    @Mapping(target = "typeOperation", ignore = true)
    @Mapping(target = "currencyCodeType", ignore=true)
    @Mapping(target="dateCreate", ignore=true)
    @Mapping(target = "dateSuccess", ignore=true)

    public abstract Transaction toEntity(TransactionDto transactionDto);

    /**
     * Custom conversion logic, need to further setup
     *
     * @param source Dto
     * @param target Entity
     */
    @AfterMapping
    void afterMapping(TransactionDto source, @MappingTarget Transaction target) {

        // map entity id
        idMap(source, target);

        // Manual mapping
       // User user = source;//userService.findById(source.getUser().getId());
         //       .orElseThrow(() -> new RuntimeException("Client not found"));
        //target.setUser(user);

    }
}
