package com.example.hostel1.helpers.pricehelpers;



import com.example.hostel1.helpers.roomhelpers.datehelpers.DaysMaker;
import com.example.hostel1.helpers.roomhelpers.datehelpers.TimestampMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.Locale;
@Component
public class TotalPrice {
    @Autowired
    private DaysMaker daysMaker;
    @Autowired
    private TimestampMaker timestampMaker;

  //  public TotalPrice(DaysMaker daysMaker,TimestampMaker timestampMaker){
    //    this.timestampMaker = timestampMaker;
      //  this.daysMaker = daysMaker;
   // }
    public String getTotalPrice(double pricePerDay, Timestamp dateIn, Timestamp dateOut) {
        Locale locale = new Locale("en", "US");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        long days = daysMaker.getDays(dateIn,dateOut);
        if (days <= 1) {
            return numberFormat.format(pricePerDay);
        } else return numberFormat.format(days * pricePerDay);
    }

    public double getTotalPrice(double pricePerDay, String dateIn, String timeIn, String dateOut, String timeOut) {
        Timestamp dayIn = timestampMaker.getTimestamp(dateIn,timeIn);
        Timestamp dayOut = timestampMaker.getTimestamp(dateOut,timeOut);
        long days = daysMaker.getDays(dayIn,dayOut);
        long pricePerDayLong = (long) (pricePerDay * 100);
        if (days <= 1) return pricePerDay;
        else return (double) (pricePerDayLong * days) / 100;
    }
}
