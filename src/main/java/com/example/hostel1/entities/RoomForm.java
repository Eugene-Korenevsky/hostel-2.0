package com.example.hostel1.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomForm {
    @JsonProperty("number")
    private int number;
    @JsonProperty("sits")
    private int sits;
    @JsonProperty("price")
    private double price;
    @JsonProperty("roomClass")
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return (room.getId() == this.getId() && room.getNumber() == this.getNumber()
                && room.getPrice() == this.getPrice() && room.getSits() == this.getSits()
                && room.getRoomClass().equals(this.getRoomClass()));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + (int) id;
        result = 37 * result + number;
        result = 37 * result + (int) price;
        result = 37 * result + sits;
        result = 37 * result + roomClass.hashCode();
        return result;
    }
}
