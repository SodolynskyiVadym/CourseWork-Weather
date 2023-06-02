package com.code.CourseWorkWeather.repositories;


import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralWeatherRepository extends JpaRepository<GeneralWeather, Integer> {
    GeneralWeather findByNameAndDateAndTime(String name, String date, String time);
    void deleteAllByDate(String date);

    void deleteAllByName(String name);

    List<GeneralWeather> findAllByNameAndDate(String name, String date);
    List<GeneralWeather> findAllByName(String name);
    List<GeneralWeather> findAllByDate(String date);
}
