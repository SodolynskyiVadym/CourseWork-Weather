package com.code.CourseWorkWeather.dao;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDAO {
    @NotEmpty(message = "Це поле не вказано")
    private String name;

    @NotEmpty(message = "Це поле не вказано")
    private String country;
}
