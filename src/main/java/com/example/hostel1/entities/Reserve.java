package com.example.hostel1.entities;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "reserve")
public class Reserve implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;


    @NotNull
    @Column(name = "TOTAL_PRICE")
    private double totalPrice;

    @NotNull
    @Column(name = "DATE_IN")
    private Timestamp dateIn;

    @NotNull
    @Column(name = "DATE_OUT")
    private Timestamp dateOut;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ID")
    private Room room;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserve reserve = (Reserve) o;
        return (reserve.getId() == this.getId() && reserve.getRoom().equals(this.getRoom()) &&
                reserve.getUser().equals(this.getUser()) && reserve.getDateIn() == this.getDateIn()
                && reserve.getDateOut() == this.getDateOut() && reserve.getTotalPrice() == this.getTotalPrice());
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) id;
        result = 37 * result + user.hashCode();
        result = 37 * result + room.hashCode();
        result = 37 * result + dateIn.hashCode();
        result = 37 * result + dateOut.hashCode();
        result = 37 * result + (int) totalPrice;
        return result;
    }
}
