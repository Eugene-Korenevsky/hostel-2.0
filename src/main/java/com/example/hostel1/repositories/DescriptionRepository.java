package com.example.hostel1.repositories;

import com.example.hostel1.entities.Description;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptionRepository extends CrudRepository<Description,Long> {
    public Description findByDescription(String description);
}
