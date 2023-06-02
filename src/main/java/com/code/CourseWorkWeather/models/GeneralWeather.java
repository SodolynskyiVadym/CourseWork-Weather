package com.code.CourseWorkWeather.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneralWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private String date;

    @Column(name = "time")
    private String time;

    @Column(name = "weather")
    private String weather;

    @Column(name = "wind_speed")
    private String windSpeed;


    @Column(name = "temperature")
    private String temperature;

    @Column(name = "precipitation")
    private String precipitation;

    public GeneralWeather(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public GeneralWeather(String name, String date, String time, String weather, String windSpeed, String temperature, String precipitation) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.weather = weather;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public boolean isNumber(String str) {
        String regex = "^\\d+$";
        return str.matches(regex);
    }


    public boolean checkOnOccupancy(){
        if ((weather.equals("не вказано") && windSpeed.equals("не вказано") && temperature.equals("не вказано") &&
                precipitation.equals("не вказано") && this.isNumber(windSpeed) && this.isNumber(temperature)
                && this.isNumber(precipitation))){
            return false;
        }
        return true;
    }
}
