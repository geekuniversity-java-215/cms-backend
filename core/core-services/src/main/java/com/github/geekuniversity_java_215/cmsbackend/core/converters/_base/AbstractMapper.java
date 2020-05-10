package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.github.geekuniversity_java_215.cmsbackend.protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;

public abstract class AbstractMapper<E ,D> {

    /**
     * Set id for Entity  //-created, -created
     */
    public void idMap(AbstractDto source,
                      AbstractEntity target) {

        Utils.fieldSetter("id", target, source.getId());
    }

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);
}
