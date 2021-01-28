package com.example.hostel1.controllers.restcontrollers.usercontrollers;

import com.example.hostel1.entities.Reserve;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userRestReserveController")
@RequestMapping(path = "user/reserves")
public class ReserveController {
    //@Autowired
    //private ReserveRepository reserveRepository;
    @Autowired
    private ReserveService reserveService;

    @DeleteMapping(value = {"{id}"})
    public ResponseEntity<?> deleteReserve(@PathVariable("id") long id) {
        try {
            reserveService.deleteById(id);
            //reserveRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(e.getValidationError(), HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
