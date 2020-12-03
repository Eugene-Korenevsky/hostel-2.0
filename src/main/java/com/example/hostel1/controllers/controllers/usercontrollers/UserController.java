package com.example.hostel1.controllers.controllers.usercontrollers;

import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReserveRepository reserveRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @GetMapping(value = {"profile"})
    public String showCabinet(Map<String, Object> model) {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User user1 = userRepository.findByEmail(user.getUsername());
            if (user1.getRole().getRole().equals("administration")) {
                model.put("user", user1);
                return "redirect:/admin/profile";
            } else {
                List<Reserve> reserves = reserveRepository.findAllByUserId(user1.getId());
                List<Order> orders = orderRepository.readByUser(user1);
                model.put("orders", orders);
                model.put("reserves", reserves);
                model.put("user", user1);
                return "profile";
            }
        } catch (Exception e) {
            return "login";
        }

    }

    @GetMapping(value = {"admin/profile"})
    public String showAdminCabinet(Map<String, Object> model) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user1 = userRepository.findByEmail(user.getUsername());
        model.put("user", user1);
        return "adminProfile";
    }

    @GetMapping(value = {"registration"})
    public String showRegisterForm(Map<String, Object> model) {
        model.put("user", new User());
        return "registration";
    }

    @PostMapping(value = {"registration"})
    public String registerUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.registration(user);
            return "login";
        } catch (EmailIsExistException | Exception e) {
            return "registration";
        }
    }
}
