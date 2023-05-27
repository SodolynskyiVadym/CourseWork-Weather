package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.Location;
import com.code.CourseWorkWeather.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService{
    LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(int id) {
        Optional<Location> result = locationRepository.findById(id);

        Location location;

        if (result.isPresent()) {
            location = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return location;
    }

    @Override
    public Location findByName(String name) {
        return null;
    }

    @Override
    public void save(Location location) {
        locationRepository.save(location);
    }


    @Transactional
    @Override
    public void deleteAllByName(String name) {
        locationRepository.deleteAllByName(name);
    }
}
