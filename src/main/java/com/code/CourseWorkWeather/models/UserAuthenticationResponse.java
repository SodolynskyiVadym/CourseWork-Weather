package com.code.CourseWorkWeather.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserAuthenticationResponse {
    private String username;
    private String password;
}
