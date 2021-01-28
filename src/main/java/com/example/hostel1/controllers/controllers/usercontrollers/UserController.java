package com.example.hostel1.controllers.controllers.usercontrollers;

import com.example.hostel1.AppProperties;
import com.example.hostel1.entities.Order;
import com.example.hostel1.entities.Reserve;
import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.OrderRepository;
import com.example.hostel1.repositories.ReserveRepository;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.OrderService;
import com.example.hostel1.servicies.ReserveService;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Controller("userUserController")
public class UserController {
    //@Autowired
    //private UserRepository userRepository;
    //@Autowired
    //private ReserveRepository reserveRepository;
    // @Autowired
    //  private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private AppProperties appProperties;

    @GetMapping(value = {"profile"})
    public String showCabinet(Map<String, Object> model) {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            User user1 = userService.findByEmail(user.getUsername());
            // userRepository.findByEmail(user.getUsername());
            if (user1.getRole().getRole().equals(appProperties.getAdminRole())) {
                model.put("user", user1);
                return "redirect:/admin/profile";
            } else {
                List<Reserve> reserves = reserveService.findAllByUser(user1);
                //reserveRepository.findAllByUserId(user1.getId());
                List<Order> orders = orderService.findAllByUser(user1);
                //orderRepository.readByUser(user1);
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
        User user1 = null;
        try {
            user1 = userService.findByEmail(user.getUsername());
            model.put("user", user1);
            return "adminProfile";
        } catch (ValidationException e) {
            model.put("error", e.getValidationError().getError());
            return "login";
        }
        //userRepository.findByEmail(user.getUsername());

    }

    @GetMapping(value = {"registration"})
    public String showRegisterForm(Map<String, Object> model) {
        model.put("user", new User());
        return "registration";
    }

    @PostMapping(value = {"registration"})
    public String registerUser(@ModelAttribute("user") User user, Map<String, Object> model) {
        try {
            userService.registration(user);
            return "login";
        } catch (EmailIsExistException e) {
            model.put("error", "user with such email is already exist");
            return "registration";
        } catch (ValidationException e) {
            model.put("error", e.getValidationError());
            return "registration";
        }
    }
}
