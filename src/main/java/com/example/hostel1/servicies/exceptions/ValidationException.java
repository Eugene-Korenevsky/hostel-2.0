package com.example.hostel1.servicies.exceptions;

import com.example.hostel1.entities.ValidationError;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class ValidationException extends Exception {


    private ValidationError validationError;

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message,ValidationError validationError){
        super(message);
        this.validationError = validationError;
    }
}
