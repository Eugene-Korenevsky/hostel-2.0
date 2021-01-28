package com.example.hostel1.controllers.controllers.usercontrollers;

import com.example.hostel1.entities.Room;
import com.example.hostel1.servicies.RoomService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@Controller("userRoomController")
@RequestMapping("rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping()
    public String showRoomList(Map<String, Object> model) {
        List<Room> rooms = roomService.findAll();
        model.put("rooms", rooms);
        return "roomList";
    }

    @GetMapping(value = {"{id}"})
    public String showRoom(Map<String, Object> model, @PathVariable("id") long id) {
        try {
            Room room = roomService.findById(id);
            model.put("room", room);
            return "room";
        } catch (ResourceNotFoundException e) {
            List<Room> rooms = roomService.findAll();
            model.put("rooms", rooms);
            model.put("error", "room is not exist");
            return "roomList";
        } catch (ValidationException e) {
            List<Room> rooms = roomService.findAll();
            model.put("rooms", rooms);
            model.put("error", "room id must not be null");
            return "roomList";
        }
    }
}
