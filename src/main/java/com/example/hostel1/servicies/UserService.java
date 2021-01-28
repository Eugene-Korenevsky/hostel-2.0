package com.example.hostel1.servicies;

import com.example.hostel1.entities.User;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;

public interface UserService extends GenericService<User> {
    public void registration(User user) throws EmailIsExistException, ValidationException;

    public User findByEmail(String email) throws ValidationException;
}
