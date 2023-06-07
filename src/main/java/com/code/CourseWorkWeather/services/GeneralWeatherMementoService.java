package com.code.CourseWorkWeather.services;

import com.code.CourseWorkWeather.memento.GeneralWeatherMemento;

public interface GeneralWeatherMementoService {
    GeneralWeatherMemento findById(int id);

    void save(GeneralWeatherMemento generalWeatherMemento);

    void deleteById(int id);
}
