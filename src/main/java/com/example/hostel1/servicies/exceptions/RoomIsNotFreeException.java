package com.example.hostel1.servicies.exceptions;

public class RoomIsNotFreeException extends Exception {
    public RoomIsNotFreeException(String message) {
        super(message);
    }
}
