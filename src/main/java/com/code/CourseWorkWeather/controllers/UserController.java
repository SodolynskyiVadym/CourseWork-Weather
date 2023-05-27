package com.code.CourseWorkWeather.controllers;


import com.code.CourseWorkWeather.models.GeneralDate;
import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;
import com.code.CourseWorkWeather.services.GeneralDateServiceImpl;
import com.code.CourseWorkWeather.services.GeneralWeatherServiceImpl;
import com.code.CourseWorkWeather.services.LocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
public class UserController {
    LocationServiceImpl locationService;
    GeneralWeatherServiceImpl generalWeatherService;
    GeneralDateServiceImpl generalDateService;

    @Autowired
    public UserController(LocationServiceImpl locationService, GeneralWeatherServiceImpl generalWeatherService,
                          GeneralDateServiceImpl generalDateService) {
        this.locationService = locationService;
        this.generalWeatherService = generalWeatherService;
        this.generalDateService = generalDateService;
    }

    @GetMapping("/")
    public String toMainPage(Model model){
        List<Location> locations = locationService.findAll();
        List<GeneralDate> dates = generalDateService.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("dates", dates);
        return "mainPage";
    }

    @GetMapping("/showDetailInfo")
    public String toDetailInfo(@RequestParam("name") String name, @RequestParam("date") String date, Model model){
        GeneralDate generalDate = generalDateService.findByDate(date.toUpperCase());
        if(generalDate == null){
            System.out.println("Not found date");
            return "errorPage";
        }
        List<GeneralWeather> weathers = generalWeatherService.findAllByNameDate(name.toUpperCase(), date.toUpperCase());
        GeneralDate nextDay = generalDateService.findByDate(generalDate.getNextDate());
        GeneralDate previousDay = generalDateService.findByDate(generalDate.getPreviousDate());
        model.addAttribute("weathers", weathers);
        model.addAttribute("nextDay", nextDay);
        model.addAttribute("previousDay", previousDay);
        return "detailInfo";

    }
}
