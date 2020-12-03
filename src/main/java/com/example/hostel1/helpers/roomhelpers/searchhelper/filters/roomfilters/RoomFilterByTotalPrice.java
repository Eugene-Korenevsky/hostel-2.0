package com.example.hostel1.helpers.roomhelpers.searchhelper.filters.roomfilters;


import com.example.hostel1.entities.Room;
import com.example.hostel1.helpers.roomhelpers.searchhelper.filters.FilterWithTwoParam;

public class RoomFilterByTotalPrice implements FilterWithTwoParam<Room, Integer, Double> {
    @Override
    public boolean doFilter(Room entity, Integer param1, Double param2) {
        return (param2 >= ((entity.getPrice() * 100) * param1) / 100);
    }
}
