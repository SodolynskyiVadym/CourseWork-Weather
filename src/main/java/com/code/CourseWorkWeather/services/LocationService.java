package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.Location;

import java.util.List;

public interface LocationService {
    List<Location> findAll();
    Location findById(int id);
    Location findByName(String name);

    void save(Location location);

    void deleteAllByName(String name);
}
