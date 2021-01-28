package com.example.hostel1.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError {
    private String error;
    private List<String> errors;
}
