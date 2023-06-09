package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.repositories.GeneralWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneralWeatherServiceImpl implements GeneralWeatherService {
    private GeneralWeatherRepository generalWeatherRepository;

    @Autowired
    public GeneralWeatherServiceImpl(GeneralWeatherRepository generalWeatherRepository) {
        this.generalWeatherRepository = generalWeatherRepository;
    }

    @Override
    public List<GeneralWeather> findAll() {
        return generalWeatherRepository.findAll();
    }

    @Override
    public void save(GeneralWeather mainWeather) {
        generalWeatherRepository.save(mainWeather);
    }

    @Override
    public GeneralWeather findByNameDateTime(String name, String date, String time) {
        return generalWeatherRepository.findByNameAndDateAndTime(name, date, time);
    }

    @Transactional
    @Override
    public void delete(GeneralWeather generalWeather){
        generalWeatherRepository.delete(generalWeather);
    }

    @Override
    public List<GeneralWeather> findAllByName(String name) {
        return generalWeatherRepository.findAllByName(name);
    }

    @Override
    public List<GeneralWeather> findAllByDate(String date) {
        return generalWeatherRepository.findAllByDate(date);
    }

    @Override
    public List<GeneralWeather> findAllByNameDate(String name, String date) {
        return generalWeatherRepository.findAllByNameAndDate(name, date).stream()
                .sorted((x, y) -> x.getId() - y.getId())
                .collect(Collectors.toList());
    }

    @Override
    public List<GeneralWeather> check() {
        return generalWeatherRepository.findAll().stream()
                .filter(GeneralWeather::checkOnOccupancy)
                .sorted((x, y) -> x.getName().compareTo(y.getName()))
                .collect(Collectors.toList());
    }
}
