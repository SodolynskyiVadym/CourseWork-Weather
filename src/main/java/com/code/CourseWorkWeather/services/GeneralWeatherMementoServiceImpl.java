package com.code.CourseWorkWeather.services;

import com.code.CourseWorkWeather.memento.GeneralWeatherMemento;
import com.code.CourseWorkWeather.repositories.GeneralWeatherMementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralWeatherMementoServiceImpl implements GeneralWeatherMementoService{
    private GeneralWeatherMementoRepository generalWeatherMementoRepository;

    @Autowired
    public GeneralWeatherMementoServiceImpl(GeneralWeatherMementoRepository generalWeatherMementoRepository) {
        this.generalWeatherMementoRepository = generalWeatherMementoRepository;
    }

    @Override
    public GeneralWeatherMemento findById(int id) {
        return generalWeatherMementoRepository.findById(id);
    }

    @Override
    public void save(GeneralWeatherMemento generalWeatherMemento) {
        generalWeatherMementoRepository.save(generalWeatherMemento);
    }

    @Override
    public void deleteById(int id) {
        generalWeatherMementoRepository.deleteById(id);
    }
}
