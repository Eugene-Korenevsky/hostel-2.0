package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Description;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.entities.ValidationError;
import com.example.hostel1.helpers.resources.RoomFormToRoom;
import com.example.hostel1.helpers.validation.MyValidator;
import com.example.hostel1.repositories.DescriptionRepository;
import com.example.hostel1.repositories.RoomRepository;
import com.example.hostel1.servicies.RoomService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.RoomIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class RoomServiceImpl extends GenericServiceImpl<Room> implements RoomService {
    private RoomRepository roomRepository;
    private DescriptionRepository descriptionRepository;
    private RoomFormToRoom roomFormToRoom;

    public RoomServiceImpl(RoomRepository roomRepository, DescriptionRepository descriptionRepository,
                           RoomFormToRoom roomFormToRoom) {
        super(roomRepository, Room.class);
        this.roomRepository = roomRepository;
        this.descriptionRepository = descriptionRepository;
        this.roomFormToRoom = roomFormToRoom;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Room create(RoomForm roomForm) throws ValidationException, RoomIsExistException {
        if (roomForm != null) {
            Room room = roomFormToRoom.roomFromRoomFormWithOutDesc(roomForm);
            Validator validator = MyValidator.getValidator();
            Set<ConstraintViolation<RoomForm>> violations = validator.validate(roomForm);
            if (violations.size() < 1) {
                Room room1 = roomRepository.findByNumber(room.getNumber());
                if (room1 == null) {
                    Set<Description> descriptions = new HashSet<>();
                    if (roomForm.getDescriptions() != null) {
                        for (String description : roomForm.getDescriptions()) {
                            descriptions.add(descriptionRepository.findByDescription(description));
                        }
                    }
                    room.setDescriptions(descriptions);
                    return roomRepository.save(room);
                } else throw new RoomIsExistException("room with such number is already exist");
            } else {
                ValidationError validationError = new ValidationError();
                ArrayList<String> errors = new ArrayList<>();
                for (ConstraintViolation<RoomForm> violation : violations) {
                    errors.add(violation.getMessage());
                }
                validationError.setErrors(errors);
                throw new ValidationException("validation error", validationError);
            }
        } else throw new ValidationException("roomForm must not be null");
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Room update(RoomForm roomForm,Long id) throws ValidationException, ResourceNotFoundException {
        if (roomForm != null && id != null) {
            if (roomRepository.existsById(id)) {
               // Validator validator = MyValidator.getValidator();
                //Set<ConstraintViolation<RoomForm>> violations = validator.validate(roomForm);
                //if (violations.size() < 1) {
                    Set<Description> descriptions = new HashSet<>();
                    for (String desc : roomForm.getDescriptions()) {
                        descriptions.add(descriptionRepository.findByDescription(desc));
                    }
                    Room room = roomFormToRoom.roomFromRoomFormWithOutDesc(roomForm);
                    room.setDescriptions(descriptions);
                    room.setId(roomForm.getId());
                    return roomRepository.save(room);
                //}else {
                  //  ValidationError validationError = new ValidationError();
                  //  ArrayList<String> errors = new ArrayList<>();
                   // for (ConstraintViolation<RoomForm> violation : violations) {
                     //   errors.add(violation.getMessage());
                   // }
                   // validationError.setErrors(errors);
                    //throw new ValidationException("validation error", validationError);
                //}
            } else throw new ResourceNotFoundException("resource not found");
        } else throw new ValidationException("roomForm or id must not be null");
    }
}
