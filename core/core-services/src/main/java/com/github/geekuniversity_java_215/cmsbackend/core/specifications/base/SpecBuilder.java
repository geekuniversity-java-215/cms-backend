package com.github.geekuniversity_java_215.cmsbackend.core.specifications.base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecBuilder<E extends AbstractEntity, S extends AbstractSpecDto> {
    Specification<E> build(S specDto);
}
