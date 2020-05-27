package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.SpringBeanUtilsEx;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

public abstract class AbstractMapper<E extends AbstractEntity, D extends AbstractDto> {

    private BaseRepoAccessService<E> baseRepoAccessService;

    public void setBaseRepoAccessService(BaseRepoAccessService<E> baseRepoAccessService) {
        this.baseRepoAccessService = baseRepoAccessService;
    }

    /**
     * Set id for Entity  //-created, -created
     */
    public void idMap(AbstractDto source,
                      AbstractEntity target) {

        Utils.fieldSetter("id", target, source.getId());
    }

    /**
     * Merge Entity converted from DTO to entity loaded from database, exclude null fields
     * @param converted
     * @return
     */
    public void merge(E fromDto) {

        E entity = baseRepoAccessService.findById(fromDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("Entity not found"));

        SpringBeanUtilsEx.CopyPropertiesExcludeNull(fromDto, entity);
    }

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);

    public abstract List<D> toDtoList(List<E> entityList);

    public abstract List<E> toEntityList(List<D> dtoList);
}
