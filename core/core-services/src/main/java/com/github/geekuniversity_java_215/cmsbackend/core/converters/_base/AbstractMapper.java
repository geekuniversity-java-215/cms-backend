package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.SpringBeanUtilsEx;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

    protected BaseRepoAccessService<E> baseRepoAccessService;

    protected Constructor<E,D> constructor;

//    public void setBaseRepoAccessService(BaseRepoAccessService<E> baseRepoAccessService) {
//        this.baseRepoAccessService = baseRepoAccessService;
//    }

//    /**
//     * Set id for Entity  //-created, -created
//     */
//    public void idMap(AbstractDto source,
//                      AbstractEntity target) {
//
//        Utils.fieldSetter("id", target, source.getId());
//    }

    /**
     * Merge Entity converted from DTO to entity loaded from database, exclude null fields
     * @param converted
     * @return
     */
    public E merge(D source, E target) {

        E result;

        // Update existing entity
        if(source.getId() != null) {
            result = baseRepoAccessService.findById(source.getId())
                .orElseThrow(() -> new IllegalArgumentException("Entity by id: " + source.getId() + " not found"));
            // Merge entity from DTO to entity loaded from DB
        }
        // Create new entity
        else {
            result = constructor.create(source, target);
        }
        
        SpringBeanUtilsEx.CopyPropertiesExcludeNull(target, result);
        return result;
    }

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);

    public abstract List<D> toDtoList(List<E> entityList);

    public abstract List<E> toEntityList(List<D> dtoList);

    // ====================================================

    // метод create нельзя размещать внутри AbstractMapper - mapstruct начнет ругань

    // allow to obtain new object from descendants classes
    protected abstract class Constructor<E extends AbstractEntity, D extends AbstractDto> {
        public abstract E create(D dto, E entity);
    }
}
