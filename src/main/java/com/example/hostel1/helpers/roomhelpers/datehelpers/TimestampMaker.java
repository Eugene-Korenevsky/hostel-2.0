package com.example.hostel1.helpers.roomhelpers.datehelpers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
public class TimestampMaker {
    public Timestamp getTimestamp(String day, String time) {
        return Timestamp.valueOf(day + " " + time + ":00");
    }
}
