package com.code.CourseWorkWeather.repositories;

import com.code.CourseWorkWeather.models.GeneralDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralDateRepository extends JpaRepository<GeneralDate, Integer> {
    void deleteAllByDate(String date);
    GeneralDate findByDate(String date);

}
