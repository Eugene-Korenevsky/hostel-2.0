package com.example.hostel1.helpers.resources;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.ValidationError;
import com.example.hostel1.helpers.validation.MyValidator;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

@Component
public class ReserveFromOrder {
    public Reserve reserveFromOrder(Order order) throws ValidationException {
        if (order != null) {
            Validator validator = MyValidator.getValidator();
            Set<ConstraintViolation<Order>> violations = validator.validate(order);
            if (violations.size() < 1) {
                Reserve reserve = new Reserve();
                reserve.setTotalPrice(order.getTotalPrice());
                reserve.setUser(order.getUser());
                reserve.setRoom(order.getRoom());
                reserve.setDateOut(order.getDateOut());
                reserve.setDateIn(order.getDateIn());
                return reserve;
            } else {
                ValidationError validationError = new ValidationError();
                ArrayList<String> errors = new ArrayList<>();
                for (ConstraintViolation<Order> violation : violations) {
                    errors.add(violation.getMessage());
                }
                validationError.setErrors(errors);
                throw new ValidationException("validation error", validationError);
            }
        } else throw new ValidationException("order must not be null");
    }
}
