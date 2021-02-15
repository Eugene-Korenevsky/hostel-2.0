package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Description;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.servicies.DescriptionService;
import com.example.hostel1.servicies.RoomService;
import com.example.hostel1.servicies.exceptions.RoomIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RequestMapping("admin/rooms")
@Controller("adminRoomController")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private DescriptionService descriptionService;

    @GetMapping()
    public String showAdminRoomsList(Map<String, Object> model) {
        List<Description> descriptions = descriptionService.findAll();
        List<Room> rooms = roomService.findAll();
        RoomForm room = new RoomForm();
        model.put("descriptionsList", descriptions);
        model.put("room", room);
        model.put("rooms", rooms);
        return "adminRoomsList";
    }


    @PostMapping()
    public String createRoom(@ModelAttribute("room") RoomForm room, Map<String, Object> model) {
        try {
            roomService.create(room);
        } catch (ValidationException e) {
            List<Description> descriptions = descriptionService.findAll();
            List<Room> rooms = roomService.findAll();
            room = new RoomForm();
            model.put("descriptionsList", descriptions);
            model.put("room", room);
            model.put("rooms", rooms);
            if (e.getValidationError().getErrors() != null) {
                model.put("errorsValidation", e.getValidationError().getErrors());
            } else {
                model.put("error", "room must not be null");
            }
            return "adminRoomsList";
        } catch (RoomIsExistException e) {
            List<Description> descriptions = descriptionService.findAll();
            List<Room> rooms = roomService.findAll();
            room = new RoomForm();
            model.put("descriptionsList", descriptions);
            model.put("room", room);
            model.put("rooms", rooms);
            model.put("error", "room with such number is already exist");
            return "adminRoomsList";
        }
        return "redirect:/admin/room";
    }
}
