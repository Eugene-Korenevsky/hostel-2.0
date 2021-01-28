package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Reserve;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("admin/reserves")
@Controller("adminReserveController")
public class ReserveController {
    // @Autowired
    // private ReserveRepository reserveRepository;
    @Autowired
    private ReserveService reserveService;

    @DeleteMapping(value = {"{id}"})
    public String cancelUserReserve(@PathVariable("id") long reserveId, Map<String, Object> model) {
        //reserveRepository.deleteById(reserveId);
        try {
            reserveService.deleteById(reserveId);
            return "redirect:/admin/reserve";
        } catch (ValidationException e) {
            List<Reserve> reserves = reserveService.findAll();
            model.put("reserves", reserves);
            model.put("error", "reserve id must not be null");
            return "adminReserves";
        } catch (ResourceNotFoundException e) {
            List<Reserve> reserves = reserveService.findAll();
            model.put("reserves", reserves);
            model.put("error", "reserve is not exist");
            return "adminReserves";
        }

    }

    @GetMapping()
    public String showAllReserves(Map<String, Object> model) {
        //Iterable<Reserve> reserves = reserveRepository.findAll();
        List<Reserve> reserves = reserveService.findAll();
        model.put("reserves", reserves);
        return "adminReserves";
    }
}
