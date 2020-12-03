package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.Description;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.repositories.DescriptionRepository;
import com.example.hostel1.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(path = "admin/room", produces = "application/json")
public class AdminRestRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DescriptionRepository descriptionRepository;

    @DeleteMapping(value = {"{id}"})
    public void deleteRoom(@PathVariable("id") long id) {
        roomRepository.deleteById(id);
    }

    @GetMapping(value = {"{id}"})
    public ResponseEntity<RoomForm> getRoom(@PathVariable("id") Long id) {
        try {
            Room room = roomRepository.findById(id).orElse(new Room());
            RoomForm roomForm = new RoomForm();
            roomForm.setPrice(room.getPrice());
            roomForm.setNumber(room.getNumber());
            roomForm.setRoomClass(room.getRoomClass());
            roomForm.setSits(room.getSits());
            roomForm.setId(room.getId());
            ArrayList<String> strings = new ArrayList<>();
            for (Description description : room.getDescriptions()) {
                strings.add(description.getDescription());
            }
            roomForm.setDescriptions(strings);
            return new ResponseEntity<RoomForm>(roomForm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<RoomForm>(HttpStatus.BAD_REQUEST);
        }

    }


    @PutMapping(produces = {MimeTypeUtils.APPLICATION_JSON_VALUE},
            consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE})
    public ResponseEntity<RoomForm> updateRoom(@RequestBody RoomForm roomForm) {
        Room room = roomRepository.findById(roomForm.getId()).orElse(new Room());
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
        roomRepository.save(room);
        return new ResponseEntity<RoomForm>(HttpStatus.OK);
    }
}
