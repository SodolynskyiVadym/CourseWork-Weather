package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;

import java.util.List;

public interface GeneralWeatherService {
    List<GeneralWeather> findAll();
    void save(GeneralWeather mainWeather);
    GeneralWeather findByNameDateTime(String name, String date, String time);

    List<GeneralWeather> findAllByNameDate(String name, String date);

    List<GeneralWeather> check();


    List<GeneralWeather> findAllByName(String name);
    List<GeneralWeather> findAllByDate(String date);

    void delete(GeneralWeather generalWeather);
}
