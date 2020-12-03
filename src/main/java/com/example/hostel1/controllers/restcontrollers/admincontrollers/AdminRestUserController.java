package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admin/user")
public class AdminRestUserController {
    @Autowired
    private UserRepository userRepository;

    /* @PutMapping(consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE})
     public User updateUser(@RequestBody User user) {
         return userRepository.save(user);
     }

     @GetMapping(value = {"{id}"})
     public User getUser(@PathVariable("id") long id) {
         return userRepository.findById(id).orElse(new User());
     }*/
    @PutMapping(produces = "application/json", consumes = "application/json")
    public User saveUser(@Valid @RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(value = {"{id}"}, produces = "application/json")
    public User showUser(@PathVariable("id") long id) {
        return userRepository.findById(id).orElse(new User());
    }
}
