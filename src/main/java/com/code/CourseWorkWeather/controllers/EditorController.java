package com.code.CourseWorkWeather.controllers;


import com.code.CourseWorkWeather.bussinesLogic.AdditionalClass;
import com.code.CourseWorkWeather.dao.GeneralDateDAO;
import com.code.CourseWorkWeather.dao.LocationDAO;
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
@RequestMapping("/editor")
public class EditorController {
    LocationServiceImpl locationService;
    GeneralWeatherServiceImpl generalWeatherService;
    GeneralDateServiceImpl generalDateService;

    @Autowired
    public EditorController(LocationServiceImpl locationService, GeneralWeatherServiceImpl generalWeatherService,
                            GeneralDateServiceImpl generalDateService) {
        this.locationService = locationService;
        this.generalWeatherService = generalWeatherService;
        this.generalDateService = generalDateService;
    }


    @GetMapping("/editorPage")
    private String toEditorPage(Model model){
        GeneralDateDAO generalDateDAO = new GeneralDateDAO();
        LocationDAO locationDAO = new LocationDAO();
        model.addAttribute("generalDateDAO", generalDateDAO);
        model.addAttribute("locationDAO" , locationDAO);
        return "editorPage";
    }

    @PostMapping("/createLocation")
    public String createLocation(@RequestParam("name") String name, @RequestParam("country") String country){
        if (AdditionalClass.checkLocation(name, country)){
            return "errorPage";
        }
        Location location = new Location(name.toUpperCase());
        locationService.save(location);
        List<GeneralDate> dates = generalDateService.findAll();
        for(GeneralDate generalDate: dates){
            AdditionalClass.saveNewWeather(location, generalDate, generalWeatherService);
        }
        return "redirect:/editor/editorPage";
    }

    @PostMapping("/createDate")
    public String createDate(@RequestParam("date") String date, @RequestParam("nextDate") String nextDate,
                             @RequestParam("previousDate") String previousDate){
        if (AdditionalClass.checkDateData(date, nextDate, previousDate)){
            return "errorPage";
        }
        GeneralDate generalDate = new GeneralDate(date.toUpperCase(), nextDate.toUpperCase(), previousDate.toUpperCase());
        generalDateService.save(generalDate);
        List<Location> locations = locationService.findAll();
        for(Location location: locations){
            AdditionalClass.saveNewWeather(location, generalDate, generalWeatherService);
        }
        return "redirect:/editor/editorPage";
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
        return "redirect:/editor/editorPage";
    }

    @PostMapping("/deleteByDate")
    String deleteDate(@RequestParam("date") String date){
        generalWeatherService.deleteAllByDate(date.toUpperCase());
        generalDateService.deleteAllByDate(date.toUpperCase());
        return "redirect:/editor/editorPage";
    }

    @PostMapping("/deleteByLocation")
    String deleteLocation(@RequestParam("name") String name){
        generalWeatherService.deleteAllByName(name.toUpperCase());
        locationService.deleteAllByName(name.toUpperCase());
        return "redirect:/editor/editorPage";
    }

    @GetMapping("/showEmptyWeathers")
    public String check(Model model){
        List<GeneralWeather> weathers = generalWeatherService.check();
        model.addAttribute("weathers", weathers);
        return "checkPage";
    }
}
