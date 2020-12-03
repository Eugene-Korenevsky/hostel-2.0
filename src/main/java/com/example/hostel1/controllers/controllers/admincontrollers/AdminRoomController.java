package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Description;
import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.repositories.DescriptionRepository;
import com.example.hostel1.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RequestMapping("admin/room")
@Controller
public class AdminRoomController {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private DescriptionRepository descriptionRepository;

    @GetMapping()
    public String showAdminRoomsList(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepository.findAll();
        Iterable<Description> descriptions = descriptionRepository.findAll();
        List<String> descNames = new ArrayList<>();
        for (Description description : descriptions) {
            descNames.add(description.getDescription());
        }
        RoomForm room = new RoomForm();
        model.put("descriptionsList", descriptions);
        model.put("room", room);
        model.put("rooms", rooms);
        return "adminRoomsList";
    }

    //!!!!!!!!!!!!!!!!
    @PostMapping()
    public String createRoom(@ModelAttribute("room") RoomForm room) {
        Room room1 = new Room();
        room1.setNumber(room.getNumber());
        room1.setPrice(room.getPrice());
        room1.setRoomClass(room.getRoomClass());
        room1.setSits(room.getSits());
        Iterable<Description> descriptions = descriptionRepository.findAll();
        HashSet<Description> descriptions1 = new HashSet<>();
        if (room.getDescriptions() != null) {
            for (String s : room.getDescriptions()) {
                for (Description description1 : descriptions) {
                    if (description1.getDescription().equals(s)) {
                        descriptions1.add(description1);
                        //room1.addDescription(description1);
                    }
                }
            }
        }
        room1.setDescriptions(descriptions1);
        roomRepository.save(room1);
        return "redirect:/admin/room";
    }
}
