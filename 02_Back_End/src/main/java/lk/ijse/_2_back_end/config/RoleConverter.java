package lk.ijse._2_back_end.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lk.ijse._2_back_end.entity.Role;

@Converter
public class RoleConverter implements AttributeConverter<Role, Integer> {
    
    @Override
    public Integer convertToDatabaseColumn(Role role) {
        if (role == null) return null;
        return role == Role.ADMIN ? 0 : 1;
    }

    @Override
    public Role convertToEntityAttribute(Integer value) {
        if (value == null) return null;
        return value == 0 ? Role.ADMIN : Role.USER;
    }
}