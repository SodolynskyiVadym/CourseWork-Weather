package com.code.CourseWorkWeather.bussinesLogic;

import com.code.CourseWorkWeather.models.GeneralDate;
import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;
import com.code.CourseWorkWeather.services.GeneralWeatherServiceImpl;

public class AdditionalClass {
    public static void saveNewWeather(Location location, GeneralDate mainDate, GeneralWeatherServiceImpl generalWeatherService){
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "НІЧ"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "РАНОК"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "ДЕНЬ"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "ВЕЧІР"));
    }
}
