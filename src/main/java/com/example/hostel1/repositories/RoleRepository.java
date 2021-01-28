package com.example.hostel1.repositories;

import com.example.hostel1.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findByRole(String role);
}
