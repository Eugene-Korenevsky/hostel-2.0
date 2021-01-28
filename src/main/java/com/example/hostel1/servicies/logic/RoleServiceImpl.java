package com.example.hostel1.servicies.logic;

import com.example.hostel1.entities.Role;
import com.example.hostel1.repositories.RoleRepository;
import com.example.hostel1.servicies.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role> implements RoleService {
    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        super(roleRepository,Role.class);
        this.roleRepository = roleRepository;
    }
}
