package com.github.geekuniversity_java_215.cmsbackend.core.converters.base;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;

import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractMapper {

    // Просто вложенный функциональный интерфейс
    //
    // Function<Long,Optional<? extends AbstractEntity>>
    // сюда передается  из к-л маппера SomeEntityMapper метод из соответствующего сервиса SomeEntity.findById(Long.id)
    // который выдает сущность по id и мы навешиваем из существующей сущности (если она есть) к-л поля
    // в данном случае created, updated, которые не передаются в DTO.
    public interface FindById extends Function<Long,Optional<? extends AbstractEntity>>{}

    /**
     * Set id, created, created
     */
    public void idMap(FindById findById,
                      AbstractDto source,
                      AbstractEntity target) {

        Utils.fieldSetter("id", target, source.getId());
    }
}
