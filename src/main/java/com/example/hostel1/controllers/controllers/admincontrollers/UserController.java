package com.example.hostel1.controllers.controllers.admincontrollers;

import com.example.hostel1.entities.Role;
import com.example.hostel1.entities.User;
import com.example.hostel1.servicies.RoleService;
import com.example.hostel1.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("admin/users")
@Controller("adminUserController")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String showAdminUserList(Map<String, Object> model) {
        Iterable<User> users = userService.findAll();
        Iterable<Role> roles = roleService.findAll();
        model.put("users", users);
        model.put("roles", roles);
        return "adminUsers";
    }
}
