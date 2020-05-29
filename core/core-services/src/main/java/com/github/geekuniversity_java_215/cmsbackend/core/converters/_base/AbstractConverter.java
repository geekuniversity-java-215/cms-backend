package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

@Service
public abstract class AbstractConverter<E extends AbstractEntity, D extends AbstractDto, S> {

    private  Validator validator;
    protected ObjectMapper objectMapper;
    protected AbstractMapper<E,D> entityMapper;

    protected Class<E> entityClass;
    protected Class<D> dtoClass;
    protected Class<S> specClass;

    // Будешь @Autowired через конструктор - придется в конструкторах наследников юзать super.constructor(...)
    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    // Json => T
    public <T> T get(JsonNode params, Class<T> clazz) {

        T result;

        // parsing request
        try {
            result = objectMapper.treeToValue(params, clazz);

            // validate
            if (result == null) {
                throw new ValidationException("Found null - validation failed");
            }

            //if(Number.class.isAssignableFrom(clazz))
        }
        catch (JsonProcessingException e) {
            throw new ParseException(0, "Id parse error", e);
        }
        return result;
    }


/*
    // Json => Long
    public Long getLong(JsonNode params) {

        Long result;

        // parsing request
        try {
            result = objectMapper.treeToValue(params, Long.class);

            // validate
            if (result == null || result < 0) {
                throw new ValidationException("Id validation failed");
            }
        }
        catch (JsonProcessingException e) {
            throw new ParseException(0, "Id parse error", e);
        }
        return result;
    }
*/


    // Json => List<T>
    public <T> List<T> getList(JsonNode params, Class<T> clazz) {


        List<T> result;
        try {

            if (params == null) {
                throw new ValidationException("IdList = null");
            }
            // https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects

            //noinspection rawtypes
            Class tArrayClass = Array.newInstance(clazz, 0).getClass();
            //noinspection unchecked
            result = (List<T>) Collections.singletonList(objectMapper.treeToValue(params, tArrayClass));

            result.forEach(l -> {
                if (l == null) {
                    throw new ValidationException("List<T> contains null elements");
                }
            });
        }
        catch (IOException e) {
            throw new ParseException(0, "idList param parse error", e);
        }

        return result;
    }

/*

    // Json => List<Long>
    public List<Long> getLongList(JsonNode params) {

        List<Long> result;
        try {

            if (params == null) {
                throw new ValidationException("IdList = null");
            }
            
            // https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
            result = Arrays.asList(objectMapper.treeToValue(params, Long[].class));

            result.forEach(l -> {
                if (l == null) {
                    throw new ValidationException("IdList contains null elements");
                }
            });
        }
        catch (JsonProcessingException e) {
            throw new ParseException(0 ,"idList param parse error", e);
        }

        return result;
    }
*/

/*

    // Json => String
    public String getId(JsonNode params) {

        String result;

        // parsing request
        try {
            result = objectMapper.treeToValue(params, String.class);

            // validate
            if (StringUtils.isBlank(result)) {
                throw new ValidationException("String validation failed");
            }
        }
        catch (JsonProcessingException e) {
            throw new ParseException(0, "String parse error", e);
        }
        return result;
    }

*/
    
    // T => Json
    public JsonNode toIdJson(AbstractEntity entity) {
        return objectMapper.valueToTree(entity.getId());
    }

    // ----------------------------------------------------------------------------




    // Json => Dto => Entity
    public E toEntity(JsonNode params)  {
        try {
            D dto = objectMapper.treeToValue(params, dtoClass);
            E result = entityMapper.toEntity(dto);
            validate(result);
            return result;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ParseException(0, "toEntity parse error", e);
        }
    }


    // Entity => Dto => Json
    public JsonNode toDtoJson(E entity) {
        try {
            D dto = entityMapper.toDto(entity);
            return objectMapper.valueToTree(dto);
        }
        catch (Exception e) {
            throw new ParseException(0, "toDtoJson convert error", e);
        }
    }


    // EntityList => Dto => Json
    public JsonNode toDtoListJson(List<E> entityList) {
        try {
            List<D> dtoList = entityMapper.toDtoList(entityList);
            return objectMapper.valueToTree(dtoList);
        }
        catch (Exception e) {
            throw new ParseException(0, "toDtoListJson convert error", e);
        }
    }


    // (Spec)Json => Dto (Specifications have no Entities)
    public Optional<S> toSpecDto(JsonNode params) {

        try {
            Optional<S> result = Optional.ofNullable(objectMapper.treeToValue(params, specClass));
            result.ifPresent(this::validateSpecDto);
            return result;
        }
        catch (ValidationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new ParseException(0, "toSpecDto convert error", e);
        }
    }



    // =================================================================================================================


    // check Entity validity
    protected void validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations.size() != 0) {
            throw new ConstraintViolationException("Entity validation failed", violations);
        }
    }


    // check SpecDto validity
    private void validateSpecDto(S specDto) {
        Set<ConstraintViolation<S>> violations = validator.validate(specDto);
        if (violations.size() != 0) {
            throw new ConstraintViolationException("Specification validation failed", violations);
        }

    }

}



//    // (Spec)Json => Dto (Specifications have no Entities)
//    public S toSpecDto(JsonNode params) {
//
//        try {
//            S result = objectMapper.treeToValue(params, specClass);
//            if(result != null) {
//                validateSpecDto(result);
//            }
//            return result;
//        }
//        catch (ValidationException e) {
//            throw e;
//        }
//        catch (Exception e) {
//            throw new ParseException(0, "toSpecDto convert error", e);
//        }
//    }
