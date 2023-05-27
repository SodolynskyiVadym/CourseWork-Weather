package com.code.CourseWorkWeather.repositories;


import com.code.CourseWorkWeather.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    void deleteAllByName(String name);

}
