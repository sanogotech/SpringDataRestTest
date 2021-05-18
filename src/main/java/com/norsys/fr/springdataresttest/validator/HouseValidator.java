package com.norsys.fr.springdataresttest.validator;

import com.norsys.fr.springdataresttest.entity.House;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class HouseValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return House.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        House house = (House) o;
        if ("test".equals(house.getName())) {
            errors.rejectValue("name", "name.invalid","test n'est pas un nom valide");
        }
    }
}
