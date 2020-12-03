package com.example.hostel1.repositories;

import com.example.hostel1.entities.Reserve;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends CrudRepository<Reserve,Long> {
    @Query("select i from Reserve i where USER_ID =:USER_ID")
    List<Reserve> findAllByUserId(@Param("USER_ID") Long id);
}
