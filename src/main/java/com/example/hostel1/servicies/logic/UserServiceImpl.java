package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Role;
import com.example.hostel1.entities.User;
import com.example.hostel1.repositories.RoleRepository;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void registration(User user) throws EmailIsExistException {
        Iterable<User> users = userRepository.findAll();
        if (isUserExist(users, user)) {
            throw new EmailIsExistException();
        } else {
            long a = 2;
            Role role = roleRepository.findById(a).orElse(new Role());
            user.setRole(role);
            userRepository.save(user);
        }

    }

    private boolean isUserExist(Iterable<User> users, User user) {
        boolean found = false;
        for (User user1 : users) {
            if (user1.getEmail().equals(user.getEmail())) {
                found = true;
                break;
            }
        }
        return found;
    }
}
