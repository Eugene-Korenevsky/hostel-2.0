package com.example.hostel1.servicies;

import com.example.hostel1.entities.Room;
import com.example.hostel1.entities.RoomForm;
import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.RoomIsExistException;
import com.example.hostel1.servicies.exceptions.ValidationException;

public interface RoomService extends GenericService<Room> {
    public Room create(RoomForm roomForm) throws ValidationException, RoomIsExistException;

    public Room update(RoomForm roomForm,Long id) throws ValidationException, ResourceNotFoundException;
}
