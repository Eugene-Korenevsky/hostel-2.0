package com.example.hostel1.helpers.roomhelpers.searchhelper.filters;

public interface Filter<T,T1> {
    public   boolean doFilter(T entity,T1 param);
}
