package com.github.geekuniversity_java_215.cmsbackend.payment.converter;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.address.AddressMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.services.ClientService;
import com.github.geekuniversity_java_215.cmsbackend.payment.dto.TransactionDto;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.Transaction;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {InstantMapper.class, AddressMapper.class})
public abstract class TransactionMapper extends AbstractMapper<Transaction, TransactionDto> {

    @Autowired
    ClientService clientService;

    @Mapping(source = "user.id", target = "userId")
    public abstract TransactionDto toDto(Transaction transaction);

    @Mapping(target = "client", ignore = true)
    public abstract Transaction toEntity(TransactionDto transactionDto);

    /**
     * Custom conversion logic, need to further setup
     * @param source Dto
     * @param target Entity
     */
    @AfterMapping
    void afterMapping(TransactionDto source, @MappingTarget Transaction target) {

        // map entity id
        idMap(source, target);

        // Manual mapping
        Client client = clientService.findById(source.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        target.setClient(client);

        target.setStatus(OrderStatus.getById(source.getStatus()));
    }

