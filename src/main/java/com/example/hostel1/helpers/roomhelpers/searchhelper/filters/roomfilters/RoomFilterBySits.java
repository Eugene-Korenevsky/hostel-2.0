package com.example.hostel1.helpers.roomhelpers.searchhelper.filters.roomfilters;


import com.example.hostel1.entities.Room;
import com.example.hostel1.helpers.roomhelpers.searchhelper.filters.Filter;

public class RoomFilterBySits implements Filter<Room, Integer> {

    @Override
    public synchronized boolean doFilter(Room room, Integer param) {
        int searchSits = param;
        return (searchSits == room.getSits());
    }


}
