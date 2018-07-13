package util.hibernate;

import model.entity.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Vladyslava_Hubenko on 7/12/2018.
 */
@Converter
public class ConverterEnum implements AttributeConverter<UserRole,String> {
    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.getName();
    }

    @Override
    public UserRole convertToEntityAttribute(String s) {
        return UserRole.valueOf(s);
    }
}
