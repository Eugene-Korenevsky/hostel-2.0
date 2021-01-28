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
    // @Autowired
    // private RoomRepository roomRepository;
    // @Autowired
    // private DescriptionRepository descriptionRepository;
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
            //roomRepository.findById(id).orElse(new Room());
            RoomForm roomForm = roomFormFromRoom.getRoomForm(room);
            /*RoomForm roomForm = new RoomForm();
            roomForm.setPrice(room.getPrice());
            roomForm.setNumber(room.getNumber());
            roomForm.setRoomClass(room.getRoomClass());
            roomForm.setSits(room.getSits());
            roomForm.setId(room.getId());
            ArrayList<String> strings = new ArrayList<>();
            for (Description description : room.getDescriptions()) {
                strings.add(description.getDescription());
            }
            roomForm.setDescriptions(strings);*/
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
        /*Room room = roomRepository.findById(roomForm.getId()).orElse(new Room());
        Iterable<Description> descriptions = descriptionRepository.findAll();
        Set<Description> roomDesc = new HashSet<>();
        System.out.println(roomForm.getPrice());
        for (Description description : descriptions) {
            for (String s : roomForm.getDescriptions()) {
                if (s.equals(description.getDescription())) {
                    roomDesc.add(description);
                    break;
                }
            }
        }
        room.setDescriptions(roomDesc);
        room.setSits(roomForm.getSits());
        room.setRoomClass(roomForm.getRoomClass());
        room.setPrice(roomForm.getPrice());
        room.setNumber(roomForm.getNumber());
        roomRepository.save(room);*/
    }
}
