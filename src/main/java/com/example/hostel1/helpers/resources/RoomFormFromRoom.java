package com.example.hostel1.helpers.resources;

import com.example.hostel1.entities.Description;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.entities.ValidationError;
import com.example.hostel1.helpers.validation.MyValidator;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class RoomFormFromRoom {
    public RoomForm getRoomForm(Room room) throws ValidationException {
        if (room != null) {
            Validator validator = MyValidator.getValidator();
            Set<ConstraintViolation<Room>> violations = validator.validate(room);
            if (violations.size() < 1) {
                RoomForm roomForm = new RoomForm();
                roomForm.setId(room.getId());
                roomForm.setNumber(room.getNumber());
                roomForm.setPrice(room.getPrice());
                roomForm.setRoomClass(room.getRoomClass());
                roomForm.setSits(room.getSits());
                ArrayList<String> descriptions = new ArrayList<>();
                for (Description description : room.getDescriptions()) {
                    descriptions.add(description.getDescription());
                }
                roomForm.setDescriptions(descriptions);
                return roomForm;
            } else {
                ValidationError validationError = new ValidationError();
                List<String> errors = new LinkedList<>();
                for (ConstraintViolation<Room> violation : violations) {
                    errors.add(violation.getMessage());
                }
                validationError.setErrors(errors);
                throw new ValidationException("room valid error", validationError);
            }
        } else throw new ValidationException("room must not be null");
    }
}
