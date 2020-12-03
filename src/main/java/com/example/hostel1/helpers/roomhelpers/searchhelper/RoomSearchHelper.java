package com.example.hostel1.helpers.roomhelpers.searchhelper;


import com.example.hostel1.entities.Room;

import java.sql.Timestamp;
import java.util.List;

public interface RoomSearchHelper {
    public List<Room> searchByTotalPriceAndSits(List<Room> rooms, Timestamp dateIn, Timestamp dateOut,
                                                double searchPrice, int sits);
}
