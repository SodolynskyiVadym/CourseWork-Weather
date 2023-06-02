package com.code.CourseWorkWeather.services;

import com.code.CourseWorkWeather.models.User;
import com.code.CourseWorkWeather.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .filter(x -> x.getRole().toString().equals("EDITOR"))
                .toList();
    }


    @Transactional
    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
