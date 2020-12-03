package com.example.hostel1.servicies;

import com.example.hostel1.entities.User;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;

public interface UserService {
    public void registration(User user) throws EmailIsExistException;
}
