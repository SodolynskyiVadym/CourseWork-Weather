package com.code.CourseWorkWeather.repositories;

import com.code.CourseWorkWeather.models.Role;
import com.code.CourseWorkWeather.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    Optional<User> findByUsername(String username);
    Optional<User> findFirstByRole(Role role);

    void deleteByUsername(String username);
}
