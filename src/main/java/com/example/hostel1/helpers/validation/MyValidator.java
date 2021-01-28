package com.example.hostel1.helpers.validation;

import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Component
public class MyValidator {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private MyValidator() {
    }

    public static Validator getValidator() {
        return validatorFactory.getValidator();
    }
}
