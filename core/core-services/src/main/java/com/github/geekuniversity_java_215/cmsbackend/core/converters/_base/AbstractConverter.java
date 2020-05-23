package com.github.geekuniversity_java_215.cmsbackend.core.converters._base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public abstract class AbstractConverter<E,D,S> {

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

    // Json => Long
    public Long getId(JsonNode params) {

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


    // Json => List<Long>
    public List<Long> getIdList(JsonNode params) {

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

    // Long => Json
    public JsonNode toIdJson(AbstractEntity entity) {
        return objectMapper.valueToTree(entity.getId());
    }

    // -------------------------------------------------------------------------------------------------

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
            throw new ParseException(0, "Dto parse error", e);
        }
    }


    // Entity => Dto => Json
    public JsonNode toDtoJson(E entity) {
        try {
            D dto = entityMapper.toDto(entity);
            return objectMapper.valueToTree(dto);
        }
        catch (Exception e) {
            throw new ParseException(0, "ToDtoJson convert error", e);
        }
    }


    // check Entity validity
    protected void validate(E entity) {
        Set<ConstraintViolation<E>> violations = validator.validate(entity);
        if (violations.size() != 0) {
            throw new ConstraintViolationException("Entity validation failed", violations);
        }
    }

