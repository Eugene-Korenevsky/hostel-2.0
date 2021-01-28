package com.example.hostel1.servicies;

import com.example.hostel1.servicies.exceptions.ResourceNotFoundException;
import com.example.hostel1.servicies.exceptions.ValidationException;

import java.util.List;

public interface GenericService<T> {
    public T findById(Long id) throws ValidationException, ResourceNotFoundException;

    public void deleteById(Long id) throws ValidationException,ResourceNotFoundException;

    public T create(T entity) throws ValidationException;

    public T update(T entity,Long id) throws ValidationException,ResourceNotFoundException;

    public List<T> findAll();
}
