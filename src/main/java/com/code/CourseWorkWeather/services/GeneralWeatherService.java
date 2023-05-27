package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.GeneralWeather;

import java.util.List;

public interface GeneralWeatherService {
    List<GeneralWeather> findAll();
    GeneralWeather findById(int id);
    void save(GeneralWeather mainWeather);
    GeneralWeather findByNameDateTime(String name, String date, String time);
    void deleteAllByDate(String date);

    List<GeneralWeather> findAllByNameDate(String name, String date);

    List<GeneralWeather> check();

    void deleteAllByName(String name);

}
