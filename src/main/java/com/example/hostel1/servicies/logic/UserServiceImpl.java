package com.example.hostel1.servicies.logic;

import com.example.hostel1.AppProperties;
import com.example.hostel1.entities.Role;
import com.example.hostel1.entities.User;
import com.example.hostel1.entities.ValidationError;
import com.example.hostel1.helpers.validation.MyValidator;
import com.example.hostel1.repositories.RoleRepository;
import com.example.hostel1.repositories.UserRepository;
import com.example.hostel1.servicies.UserService;
import com.example.hostel1.servicies.exceptions.EmailIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Set;


@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private AppProperties appProperties;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, AppProperties appProperties) {
        super(userRepository, User.class);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.appProperties = appProperties;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void registration(User user) throws EmailIsExistException, ValidationException {
        Role role = roleRepository.findByRole(appProperties.getDefaultRole());
        if (user != null && role != null) {
            user.setRole(role);
            Validator validator = MyValidator.getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (violations.size() < 1) {
                if (isUserExist(user)) {
                    throw new EmailIsExistException();
                } else {
                    userRepository.save(user);
                }
            } else {
                ValidationError validationError = new ValidationError();
                ArrayList<String> errors = new ArrayList<>();
                for (ConstraintViolation<User> constraintViolation : violations) {
                    errors.add(constraintViolation.getMessage());
                }
                validationError.setErrors(errors);
                throw new ValidationException("validation error", validationError);
            }
        } else throw new ValidationException("user must not be null");
    }

    @Override
    public User findByEmail(String email) throws ValidationException {
        if (email != null) {
            return userRepository.findByEmail(email);
        } else throw new ValidationException("email must not be null");
    }

    private boolean isUserExist(User user) {
        return userRepository.findByEmail(user.getEmail()) != null;
    }
}
