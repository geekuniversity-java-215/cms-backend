package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractMapper {

    /**
     * Set id for Entity  //-created, -created
     */
    public void idMap(AbstractDto source,
                      AbstractEntity target) {

        Utils.fieldSetter("id", target, source.getId());
    }
}
