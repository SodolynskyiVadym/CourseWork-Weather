package com.code.CourseWorkWeather.repositories;

import com.code.CourseWorkWeather.memento.GeneralWeatherMemento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneralWeatherMementoRepository extends JpaRepository<GeneralWeatherMemento, Integer> {
    GeneralWeatherMemento findById(int id);

    void deleteById(int id);
}
