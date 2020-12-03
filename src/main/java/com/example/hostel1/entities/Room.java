package com.example.hostel1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "room")
public class Room implements Serializable {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "NUMBER")
    @Min(value = 1,message = "min value is 1")
    private int number;

    @NotNull
    @Column(name = "CLASS")
    private String roomClass;

    @NotNull
    @Column(name = "SITS")
    @Min(value = 1,message = "min value is 1")
    private Integer sits;

    @NotNull
    @Column(name = "PRICE")
    @Min(value = 0,message = "min value is 0")
    private double price;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "room_description",
            joinColumns = @JoinColumn(name = "ROOM_ID"),
            inverseJoinColumns = @JoinColumn(name = "DESCRIPTION_ID"))
    private Set<Description> descriptions = new HashSet<>();


}
