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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy ");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(timeFormat.format(now));
        model.addAttribute("locations", locations);
        model.addAttribute("dates", dates);
        model.addAttribute("globalTime", timeFormat.format(now));
        return "mainPage";
    }

    @GetMapping("/searchByLocation")
    public String findLocationDate(@RequestParam("name") String name, Model model){
        if(locationService.findByName(name.toUpperCase()) == null){
            return "errorPage";
        }
        String date = generalDateService.findAll().get(0).getDate();
        if(generalDateService.findAll().size() == 0){
            System.out.println("Not found date");
            return "errorPage";
        }
        GeneralDate generalDate = generalDateService.findAll().get(0);

        List<GeneralWeather> weathers = generalWeatherService.findAllByNameDate(name.toUpperCase(), date.toUpperCase());
        GeneralDate nextDay = generalDateService.findByDate(generalDate.getNextDate());
        GeneralDate previousDay = generalDateService.findByDate(generalDate.getPreviousDate());
        model.addAttribute("weathers", weathers);
        model.addAttribute("nextDay", nextDay);
        model.addAttribute("previousDay", previousDay);
        model.addAttribute("country", locationService.findByName(name).getCountry());
        return "detailInfo";
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
        String country = locationService.findByName(name).getCountry();
        model.addAttribute("weathers", weathers);
        model.addAttribute("nextDay", nextDay);
        model.addAttribute("previousDay", previousDay);
        System.out.println(country);
        model.addAttribute("country", country);
        return "detailInfo";
    }

    @GetMapping("/toAccessDeniedPage")
    public String toAccessDeniedPAge(){
        return "accessDeniedPage";
    }

    @GetMapping("/contactPage")
    public String toContactPage(){
        return "contact";
    }
}
