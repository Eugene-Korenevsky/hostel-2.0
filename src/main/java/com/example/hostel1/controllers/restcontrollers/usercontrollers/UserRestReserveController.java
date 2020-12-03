package com.example.hostel1.controllers.restcontrollers.usercontrollers;

import com.example.hostel1.entities.Reserve;
import com.example.hostel1.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "user/reserve")
public class UserRestReserveController {
    @Autowired
    private ReserveRepository reserveRepository;

    @DeleteMapping(value = {"{id}"})
    public ResponseEntity<Reserve> deleteReserve(@PathVariable("id") long id) {
        try {
            reserveRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
