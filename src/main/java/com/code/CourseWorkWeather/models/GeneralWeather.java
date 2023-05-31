package com.code.CourseWorkWeather.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "general_weather")
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

    public GeneralWeather(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public GeneralWeather(int id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public boolean isNumber(String str) {
        String regex = "^\\d+$";
        return str.matches(regex);
    }


    public boolean checkOnOccupancy(){
        if ((weather!=null && windSpeed!=null && temperature!=null && this.isNumber(windSpeed) &&
                this.isNumber(temperature))){
            return false;
        }
        return true;
    }
}
