package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.GeneralDate;
import java.util.List;

public interface GeneralDateService {
    List<GeneralDate> findAll();
    void save(GeneralDate mainDate);
    void deleteAllByDate(String date);

    GeneralDate findByDate(String date);
    boolean existByDate(String date);
}
