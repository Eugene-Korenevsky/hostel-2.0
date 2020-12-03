package com.example.hostel1.helpers.orderhelpers;


import com.example.hostel1.entities.Reserve;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import com.example.hostel1.repositories.ReserveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
public class IsRoomFreeInDates {
    @Autowired
    private  TimestampMaker timestampMaker;
    @Autowired
    private  ReserveRepository reserveRepository;

    //public IsRoomFreeInDates(TimestampMaker timestampMaker,ReserveRepository reserveRepository){
      //  this.reserveRepository = reserveRepository;
      //  this.timestampMaker = timestampMaker;
   // }
    public boolean IsRoomFree(long roomId, String dateIn, String timeIn, String dateOut, String timeOut) {
        boolean found = false;
        long inDate = timestampMaker.getTimestamp(dateIn,timeIn).getTime();
        long outDate = timestampMaker.getTimestamp(dateOut,timeOut).getTime();
        return isFree(inDate, outDate, roomId);
    }

    public boolean isRoomFree(long roomId, Timestamp dateIn, Timestamp dateOut) {
        boolean found = false;
        long inDate = dateIn.getTime();
        long outDate = dateOut.getTime();
        return isFree(inDate, outDate, roomId);

    }

    private boolean isFree(long inDate, long outDate,long roomId) {
        boolean found = false;
        Iterable<Reserve> reserves = reserveRepository.findAll();
        for (Reserve reserve : reserves) {
            if (roomId == reserve.getRoom().getId()) {
                long reserveDateIn = reserve.getDateIn().getTime();
                long reserveDateOut = reserve.getDateOut().getTime();
                if ((inDate >= reserveDateIn && inDate <= reserveDateOut) ||
                        (outDate >= reserveDateIn && outDate <= reserveDateOut)) {
                    found = true;
                    break;
                }
            }
        }
        return !found;
    }
}
