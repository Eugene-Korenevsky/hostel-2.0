package com.example.hostel1.helpers.roomhelpers.datehelpers;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
@Component
public class DaysMaker {
    public int getDays(Timestamp dateIn, Timestamp dateOut) {
        StringBuilder inDate = new StringBuilder(dateIn.toString());
        StringBuilder outDate = new StringBuilder(dateOut.toString());
        int index = inDate.indexOf(" ");
        inDate.delete(index, inDate.length());
        index = outDate.indexOf(" ");
        outDate.delete(index, outDate.length());
        long dateInLong = Date.valueOf(inDate.toString()).getTime();
        long dateOutLong = Date.valueOf(outDate.toString()).getTime();
        return (int) (dateOutLong - dateInLong) / 86400000;
    }
}
