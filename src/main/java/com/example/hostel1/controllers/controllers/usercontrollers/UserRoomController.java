package com.example.hostel1.controllers.controllers.usercontrollers;

import com.example.hostel1.entities.Room;
import com.example.hostel1.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("room")
public class UserRoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping()
    public String showRoomList(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepository.findAll();
        model.put("rooms",rooms);
        return "roomList";
    }

    @GetMapping(value = {"{id}"})
    public String showRoom(Map<String, Object> model, @PathVariable("id") long id) {
        Room room = roomRepository.findById(id).orElse(new Room());
        model.put("room", room);
        return "room";
    }
}
