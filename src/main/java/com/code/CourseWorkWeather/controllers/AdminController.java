package com.code.CourseWorkWeather.controllers;


import com.code.CourseWorkWeather.models.GeneralDate;
import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;
import com.code.CourseWorkWeather.services.GeneralDateServiceImpl;
import com.code.CourseWorkWeather.services.GeneralWeatherServiceImpl;
import com.code.CourseWorkWeather.services.LocationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    LocationServiceImpl locationService;
    GeneralWeatherServiceImpl generalWeatherService;
    GeneralDateServiceImpl generalDateService;

    @Autowired
    public AdminController(LocationServiceImpl locationService, GeneralWeatherServiceImpl generalWeatherService,
                          GeneralDateServiceImpl generalDateService) {
        this.locationService = locationService;
        this.generalWeatherService = generalWeatherService;
        this.generalDateService = generalDateService;
    }

    private void saveNewWeather(Location location, GeneralDate mainDate){
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "MIDNIGHT"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "MORNING"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "MIDDAY"));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "EVENING"));
    }

    @PostMapping("/createLocation")
    public String createLocation(@RequestParam("name") String name){
        Location location = new Location(name.toUpperCase());
        locationService.save(location);
        List<GeneralDate> dates = generalDateService.findAll();
        for(GeneralDate generalDate: dates){
            this.saveNewWeather(location, generalDate);
        }
        return "redirect:/";
    }

    @PostMapping("/createDate")
    public String createDate(@RequestParam("date") String date, @RequestParam("nextDate") String nextDate,
                             @RequestParam("previousDate") String previousDate){
        GeneralDate generalDate = new GeneralDate(date.toUpperCase(), nextDate.toUpperCase(), previousDate.toUpperCase());
        generalDateService.save(generalDate);
        List<Location> locations = locationService.findAll();
        for(Location location: locations){
            this.saveNewWeather(location, generalDate);
        }
        return "redirect:/";
    }

    @PostMapping("/showRedactorPage")
    public String toRedactorPage(@RequestParam("name") String name, @RequestParam("date") String date,
                                 @RequestParam("time") String time, Model model){
        GeneralWeather weather = generalWeatherService.findByNameDateTime(name.toUpperCase(), date.toUpperCase(), time.toUpperCase());
        if(weather==null){
            return "errorPage";
        }
        model.addAttribute("generalWeather", weather);
        return "redactor";
    }

    @PostMapping("/save")
    public String saveMainWeathers(@Valid @ModelAttribute("generalWeather") GeneralWeather generalWeather, BindingResult result) {
        if(result.hasErrors()){
            return "redactor";
        }
        generalWeatherService.save(generalWeather);
        return "redirect:/";
    }

    @PostMapping("/deleteByDate")
    String deleteDate(@RequestParam("date") String date){
        generalWeatherService.deleteAllByDate(date.toUpperCase());
        generalDateService.deleteAllByDate(date.toUpperCase());
        return "redirect:/";
    }

    @PostMapping("/deleteByLocation")
    String deleteLocation(@RequestParam("name") String name){
        generalWeatherService.deleteAllByName(name.toUpperCase());
        locationService.deleteAllByName(name.toUpperCase());
        return "redirect:/";
    }

    @GetMapping("/showEmptyWeathers")
    public String check(Model model){
        List<GeneralWeather> weathers = generalWeatherService.check();
        model.addAttribute("weathers", weathers);
        return "checkPage";
    }
}
