package com.example.hostel1.repositories;

import com.example.hostel1.entities.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room,Long> {
    public Room findByNumber(int number);
}
