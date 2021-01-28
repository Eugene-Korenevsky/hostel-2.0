package com.example.hostel1.helpers.resources;

import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.entities.ValidationError;
import com.example.hostel1.helpers.validation.MyValidator;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;

@Component
public class RoomFormToRoom {

    public Room roomFromRoomFormWithOutDesc(RoomForm roomForm) throws ValidationException {
        if (roomForm != null) {
            Validator validator = MyValidator.getValidator();
            Set<ConstraintViolation<RoomForm>> violations = validator.validate(roomForm);
            if (violations.size() < 1) {
                Room room = new Room();
                room.setNumber(roomForm.getNumber());
                room.setPrice(roomForm.getPrice());
                room.setRoomClass(roomForm.getRoomClass());
                room.setSits(roomForm.getSits());
                return room;
            } else {
                ValidationError validationError = new ValidationError();
                ArrayList<String> errors = new ArrayList<>();
                for (ConstraintViolation<RoomForm> violation : violations){
                    errors.add(violation.getMessage());
                }
                validationError.setErrors(errors);
                throw new ValidationException("validation error",validationError);
            }
        } else throw new ValidationException("roomForm must not be null");
    }
}
