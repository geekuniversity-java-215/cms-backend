package com.github.geekuniversity_java_215.cmsbackend.core.data.enums.converters;

import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) {
            return null;
        }
        return role.getCode();
    }

    @Override
    public UserRole convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return UserRole.getByCode(code);
    }
}