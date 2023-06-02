package com.code.CourseWorkWeather.services;

import com.code.CourseWorkWeather.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    void deleteByUsername(String username);

}
