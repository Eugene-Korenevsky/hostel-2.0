package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Role;
import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.RoleRepository;
import com.example.hostel1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("admin/user")
@Controller
public class AdminUserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping()
    public String showAdminUserList(Map<String, Object> model) {
        Iterable<User> users = userRepository.findAll();
        Iterable<Role> roles = roleRepository.findAll();
        model.put("users", users);
        model.put("roles", roles);
        return "adminUsers";
    }
}
