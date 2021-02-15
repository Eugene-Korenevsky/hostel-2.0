package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.helpers.resources.RoomFormFromRoom;
import com.example.hostel1.servicies.DescriptionService;
import com.example.hostel1.servicies.RoomService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

@RestController("adminRestRoomController")
@RequestMapping(path = "admin/rooms", produces = "application/json")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private DescriptionService descriptionService;

    @Autowired
    private RoomFormFromRoom roomFormFromRoom;

    @DeleteMapping(value = {"{id}"})
    public ResponseEntity<?> deleteRoom(@PathVariable("id") long id) {
        try {
            roomService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = {"{id}"})
    public ResponseEntity<?> getRoom(@PathVariable("id") Long id) {
        try {
            Room room = roomService.findById(id);
            RoomForm roomForm = roomFormFromRoom.getRoomForm(room);
            return new ResponseEntity<RoomForm>(roomForm, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping(value = {"{id}"}, produces = {MimeTypeUtils.APPLICATION_JSON_VALUE},
            consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateRoom(@RequestBody RoomForm roomForm,
                                        @PathVariable("id") long id) {
        try {
            roomForm = roomFormFromRoom.getRoomForm(roomService.update(roomForm, id));
            return new ResponseEntity<>(roomForm, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
