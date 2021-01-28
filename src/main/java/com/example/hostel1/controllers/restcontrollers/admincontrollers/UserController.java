package com.example.hostel1.controllers.restcontrollers.admincontrollers;

import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("adminRestUserController")
@RequestMapping("admin/users")
public class UserController {

    // @Autowired
    // private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PutMapping(value = {"{id}"}, produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> saveUser(@Valid @RequestBody User user, @PathVariable("id") long id) {
        if (id == user.getId()) {
            try {
                user = userService.update(user, id);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } catch (ValidationException e) {
                return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
            } catch (ResourceNotFoundException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        //userRepository.save(user);
    }

    @GetMapping(value = {"{id}"}, produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        try {
            User user = userService.findById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        //userRepository.findById(id).orElse(new User());
    }
}
