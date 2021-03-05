package com.example.hostel1.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
public class RoomForm implements Serializable {

    @JsonProperty("number")
    @NotNull
    @Min(value = 1,message = "min value is 1")
    private int number;


    @JsonProperty("sits")
    @Min(value = 1,message = "min value is 1")
    @NotNull
    private int sits;


    @JsonProperty("price")
    @Min(value = 0,message = "min value is 0")
    @NotNull
    private double price;


    @JsonProperty("roomClass")
    @Size(min = 1,max = 2,message = "class must have at least 1 letter and not larger then 2 letters")
    @NotNull
    private String roomClass;


    @JsonProperty("descriptions")
    private List<String> descriptions;


    @JsonProperty("id")
    private long id;

    public RoomForm(int number, int sits, double price, String roomClass, List<String> descriptions, long id) {
        this.number = number;
        this.sits = sits;
        this.price = price;
        this.roomClass = roomClass;
        this.descriptions = descriptions;
        this.id = id;
    }

    public RoomForm() {

    }
}
