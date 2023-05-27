package com.code.CourseWorkWeather.services;


import com.code.CourseWorkWeather.models.GeneralDate;
import com.code.CourseWorkWeather.repositories.GeneralDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class GeneralDateServiceImpl implements GeneralDateService {
    GeneralDateRepository generalDateRepository;

    @Autowired
    public GeneralDateServiceImpl(GeneralDateRepository generalDateRepository) {
        this.generalDateRepository = generalDateRepository;
    }

    @Override
    public List<GeneralDate> findAll() {
        return generalDateRepository.findAll().stream()
                .sorted((x, y) -> x.getDate().compareTo(y.getDate()))
                .collect(Collectors.toList());
    }

    @Override
    public GeneralDate findById(int id) {
        Optional<GeneralDate> result = generalDateRepository.findById(id);

        GeneralDate mainDate;

        if (result.isPresent()) {
            mainDate = result.get();
        }
        else {
            throw new RuntimeException("Did not find employee id - " + id);
        }
        return mainDate;
    }

    @Override
    public void save(GeneralDate mainDate) {
        generalDateRepository.save(mainDate);
    }

    @Transactional
    @Override
    public void deleteAllByDate(String date) {
        generalDateRepository.deleteAllByDate(date);
    }

    @Override
    public GeneralDate findByDate(String date) {
        return generalDateRepository.findByDate(date);
    }
}
