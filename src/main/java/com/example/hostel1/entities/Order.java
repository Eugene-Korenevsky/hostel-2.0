package com.example.hostel1.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@EqualsAndHashCode
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;


    @NotNull
    @Column(name = "TOTAL_PRICE")
    @Min(value = 0, message = "min price can not be less then 0")
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

}
