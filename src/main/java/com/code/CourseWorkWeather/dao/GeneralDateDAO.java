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
public class GeneralDateDAO {

    @NotEmpty(message = "Це поле не вказано")

    private String date;

    @NotEmpty(message = "Це поле не вказано")

    private String nextDate;

    @NotEmpty(message = "Це поле не вказано")
    private String previousDate;
}
