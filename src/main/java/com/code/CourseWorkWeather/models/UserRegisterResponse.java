package com.code.CourseWorkWeather.models;


import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserRegisterResponse {
    private String name;

    @Size(min = 5, message = "Мінімальна кількість символів 5")
    private String username;

    @Size(min = 5, message = "Мінімальна кількість символів 5")
    private String password;

    private String passwordConfirmation;
}
