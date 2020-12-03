package com.example.hostel1.helpers.orderhelpers;

import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderCorrectDate {
    @Autowired
    private TimestampMaker timestampMaker;

    public OrderCorrectDate(TimestampMaker timestampMaker) {
        this.timestampMaker = timestampMaker;
    }

    public boolean isCorrectDates(String dateIn, String timeIn, String dateOut, String timeOut) {
        long inDate = timestampMaker.getTimestamp(dateIn, timeIn).getTime();
        long outDate = timestampMaker.getTimestamp(dateOut, timeOut).getTime();
        long currentDate = new Date().getTime();
        return currentDate < inDate && inDate < outDate;
    }
}
