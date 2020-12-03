package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Reserve;
import com.example.hostel1.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("admin/reserve")
@Controller
public class AdminReserveController {
    @Autowired
    private ReserveRepository reserveRepository;

    @DeleteMapping(value = {"{id}"})
    public String cancelUserReserve(@PathVariable("id") long reserveId) {
        reserveRepository.deleteById(reserveId);
        return "redirect:/admin/reserve";
    }

    @GetMapping()
    public String showAllReserves(Map<String, Object> model) {
        Iterable<Reserve> reserves = reserveRepository.findAll();
        model.put("reserves", reserves);
        return "adminReserves";
    }
}
