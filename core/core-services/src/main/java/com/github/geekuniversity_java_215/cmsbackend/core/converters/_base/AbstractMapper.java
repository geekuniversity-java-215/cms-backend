package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.SpringBeanUtilsEx;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;


@MapperConfig(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
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
     * Merge Entity converted from DTO(target) to entity loaded from database(result),
     * exclude null fields on target and PersistentBag(lazy-loaded fields) on result
     * @param source - unconverted Dto from client
     * @param target - converted Dto -> Entity
     * result - entity uploaded by target.id from DB
     * @return merge result
     */
    public E merge(D source, E target) {



        E result;


        // source.getId() - cause entity.id has protected setter and target.id always be null
        // Update existing entity
        if(source.getId() != null) {
            //result = baseRepoAccessService.findByIdEager(source.getId());  // Загружаем сущность целиком, без lazy initialization
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

    //@Mapping(target = "{created,updated,enabled}", ignore = true)
//    @Mapping(target = "enabled", ignore = true)
//    @Mapping(target = "created", ignore = true)
//    @Mapping(target = "updated", ignore = true)
    public abstract D toDto(E entity);

    public abstract List<D> toDtoList(List<E> entityList);

    public abstract List<E> toEntityList(List<D> dtoList);

    // ====================================================

    // метод create нельзя размещать внутри AbstractMapper - mapstruct начнет ругань,
    // что он не знает, куда прикрутить этот ваш create() и что им делать.

    // allow to obtain new object from descendants classes
    protected abstract class Constructor<E extends AbstractEntity, D extends AbstractDto> {
        public abstract E create(D dto, E entity);
    }
}
