package com.code.CourseWorkWeather.dao;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralWeatherDAO{
    private String name;

    private String date;

    private String time;

    @NotEmpty(message = "Поле не  може бути пустим")
    private String weather;


    @NotEmpty(message = "Поле не може бути пустим")
    @Pattern(regexp = "^[1-9]\\d*$", message = "Це поле має бути додатнім числом")
    private String windSpeed;

    @NotEmpty(message = "Поле не може бути пустим")
    @Pattern(regexp = "^-?\\d+$", message = "Це поле має бути числом")
    private String temperature;

    @NotEmpty(message = "Поле не може бути пустим")
    @Pattern(regexp = "^-?\\d+$", message = "Це поле має бути числом")
    private String precipitation;

    public GeneralWeatherDAO(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}
