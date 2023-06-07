package com.code.CourseWorkWeather.memento;


import com.code.CourseWorkWeather.models.GeneralWeather;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GeneralWeatherMemento{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    int id;
    String weather;
    String windSpeed;
    String temperature;
    String precipitation;

    public GeneralWeatherMemento(String weather, String windSpeed, String temperature, String precipitation) {
        this.weather = weather;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    public void setGeneralWeatherMemento(GeneralWeather generalWeather){
        this.setWeather(generalWeather.getWeather());
        this.setWindSpeed(generalWeather.getWindSpeed());
        this.setTemperature(generalWeather.getTemperature());
        this.setPrecipitation(generalWeather.getPrecipitation());
    }
}
