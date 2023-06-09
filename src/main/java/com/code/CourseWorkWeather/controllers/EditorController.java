package com.code.CourseWorkWeather.controllers;


import com.code.CourseWorkWeather.dao.GeneralDateDAO;
import com.code.CourseWorkWeather.dao.GeneralWeatherDAO;
import com.code.CourseWorkWeather.dao.LocationDAO;
import com.code.CourseWorkWeather.memento.GeneralWeatherMemento;
import com.code.CourseWorkWeather.models.GeneralDate;
import com.code.CourseWorkWeather.models.GeneralWeather;
import com.code.CourseWorkWeather.models.Location;
import com.code.CourseWorkWeather.services.GeneralDateServiceImpl;
import com.code.CourseWorkWeather.services.GeneralWeatherMementoServiceImpl;
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
    GeneralWeatherMementoServiceImpl generalWeatherMementoService;


    @Autowired
    public EditorController(LocationServiceImpl locationService, GeneralWeatherServiceImpl generalWeatherService,
                            GeneralDateServiceImpl generalDateService, GeneralWeatherMementoServiceImpl generalWeatherMementoService) {
        this.locationService = locationService;
        this.generalWeatherService = generalWeatherService;
        this.generalDateService = generalDateService;
        this.generalWeatherMementoService = generalWeatherMementoService;
    }

    private void saveNewWeather(Location location, GeneralDate mainDate, GeneralWeatherServiceImpl generalWeatherService){
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "НІЧ",
                "не вказано", "не вказано", "не вказано", "не вказано",-1));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "РАНОК",
                "не вказано", "не вказано", "не вказано", "не вказано", -1));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "ДЕНЬ",
                "не вказано", "не вказано", "не вказано", "не вказано", -1));
        generalWeatherService.save(new GeneralWeather(location.getName(), mainDate.getDate(), "ВЕЧІР",
                "не вказано", "не вказано", "не вказано", "не вказано", -1));
    }


    @GetMapping("/toEditorPage")
    private String toEditorPage(Model model){
        GeneralDateDAO generalDateDAO = new GeneralDateDAO();
        LocationDAO locationDAO = new LocationDAO();
        model.addAttribute("generalDateDAO", generalDateDAO);
        model.addAttribute("locationDAO" , locationDAO);
        model.addAttribute("locationsList", locationService.findAll());
        model.addAttribute("dateList", generalDateService.findAll());
        return "editorPage";
    }

    @PostMapping("/createLocation")
    public String createLocation(@Valid @ModelAttribute("locationDAO") LocationDAO locationDAO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "errorPage";
        }

        Location location = new Location(locationDAO.getName().toUpperCase(), locationDAO.getCountry().toUpperCase());
        if(locationService.existByName(locationDAO.getName().toUpperCase())){
            return "errorPage";
        }
        locationService.save(location);
        List<GeneralDate> dates = generalDateService.findAll();
        for(GeneralDate generalDate: dates){
            this.saveNewWeather(location, generalDate, generalWeatherService);
        }
        return "redirect:/editor/toEditorPage";
    }

    @PostMapping("/createDate")
    public String createDate(@Valid @ModelAttribute("generalDateDAO") GeneralDateDAO generalDateDAO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "editorPage";
        }
        if(generalDateService.existByDate(generalDateDAO.getDate().toUpperCase())){
            return "errorPage";
        }
        GeneralDate generalDate = new GeneralDate(generalDateDAO.getDate().toUpperCase(),
                generalDateDAO.getNextDate().toUpperCase(), generalDateDAO.getPreviousDate().toUpperCase());
        generalDateService.save(generalDate);
        List<Location> locations = locationService.findAll();
        for(Location location: locations){
            this.saveNewWeather(location, generalDate, generalWeatherService);
        }
        return "redirect:/editor/toEditorPage";
    }

    @PostMapping("/showRedactorPage")
    public String toRedactorPage(@RequestParam("name") String name, @RequestParam("date") String date,
                                 @RequestParam("time") String time, Model model){
        GeneralWeather weather = generalWeatherService.findByNameDateTime(name.toUpperCase(), date.toUpperCase(), time.toUpperCase());
        if(weather==null){
            return "errorPage";
        }
        boolean haveHistory = (weather.getIdMemento() != -1);

        GeneralWeatherDAO generalWeatherDAO = new GeneralWeatherDAO(name.toUpperCase(), date.toUpperCase(), time.toUpperCase(),
                weather.getWeather(), weather.getWindSpeed(), weather.getTemperature(), weather.getPrecipitation());
        model.addAttribute("haveHistory", haveHistory);
        model.addAttribute("generalWeather", generalWeatherDAO);
        model.addAttribute("idOfMemento", weather.getIdMemento());
        return "redactor";
    }


    @PostMapping("/showRedactorPageMemento")
    public String toRedactorPageMemento(@RequestParam("name") String name, @RequestParam("date") String date,
                                        @RequestParam("time") String time, @RequestParam("idOfMemento") int idOfMemento,
                                        Model model){
        GeneralWeatherMemento weatherMemento = generalWeatherMementoService.findById(idOfMemento);
        GeneralWeatherDAO generalWeatherDAO = new GeneralWeatherDAO(name, date, time, weatherMemento.getWeather(), weatherMemento.getWindSpeed(),
                weatherMemento.getTemperature(), weatherMemento.getPrecipitation());

        model.addAttribute("generalWeather", generalWeatherDAO);
        model.addAttribute("haveHistory", false);
        model.addAttribute("idOfMemento", weatherMemento.getId());
        return "redactor";
    }

    @PostMapping("/save")
    public String saveMainWeathers(@Valid @ModelAttribute("generalWeather") GeneralWeatherDAO generalWeatherDAO,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "redactor";
        }
        GeneralWeather generalWeather = generalWeatherService.findByNameDateTime(generalWeatherDAO.getName(), generalWeatherDAO.getDate(),
                generalWeatherDAO.getTime());


        GeneralWeatherMemento generalWeatherMemento;
        if (generalWeather.getIdMemento() == -1){
            generalWeatherMemento = new GeneralWeatherMemento();
            generalWeatherMemento.setGeneralWeatherMemento(generalWeather);
        }else {
            generalWeatherMemento = generalWeatherMementoService.findById(generalWeather.getIdMemento());
            generalWeatherMemento.setGeneralWeatherMemento(generalWeather);
        }
        generalWeatherMementoService.save(generalWeatherMemento);
        generalWeather.setIdMemento(generalWeatherMemento.getId());

        generalWeather.setTemperature(generalWeatherDAO.getTemperature());
        generalWeather.setWeather(generalWeatherDAO.getWeather());
        generalWeather.setWindSpeed(generalWeatherDAO.getWindSpeed());
        generalWeather.setPrecipitation(generalWeatherDAO.getPrecipitation());

        generalWeatherService.save(generalWeather);
        return "redirect:/editor/toEditorPage";
    }

    @PostMapping("/deleteByDate")
    String deleteDate(@RequestParam("date") String date){
        List<GeneralWeather> generalWeathers = generalWeatherService.findAllByDate(date.toUpperCase());

        for (GeneralWeather weather : generalWeathers){
            if (weather.getIdMemento() != -1){
                generalWeatherMementoService.deleteById(weather.getIdMemento());
            }
            generalWeatherService.delete(weather);
        }
//        generalWeatherService.deleteAllByDate(date.toUpperCase());

        generalDateService.deleteAllByDate(date.toUpperCase());
        return "redirect:/editor/toEditorPage";
    }

    @PostMapping("/deleteByLocation")
    String deleteLocation(@RequestParam("name") String name){

        List<GeneralWeather> generalWeathers = generalWeatherService.findAllByName(name.toUpperCase());

        for (GeneralWeather weather : generalWeathers){
            if (weather.getIdMemento() != -1){
                generalWeatherMementoService.deleteById(weather.getIdMemento());
            }
            generalWeatherService.delete(weather);
        }
//        generalWeatherService.deleteAllByName(name.toUpperCase());
        locationService.deleteAllByName(name.toUpperCase());
        return "redirect:/editor/toEditorPage";
    }

    @GetMapping("/showEmptyWeathers")
    public String check(Model model){
        List<GeneralWeather> weathers = generalWeatherService.check();
        model.addAttribute("weathers", weathers);
        return "checkPage";
    }

    @PostMapping("/updateLocation")
    public String updateLocation(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName,
                                 @RequestParam("newCountry") String newCountry){
        oldName = oldName.toUpperCase();
        newName = newName.toUpperCase();
        newCountry = newCountry.toUpperCase();
        Location location = locationService.findByName(oldName);
        if (location==null){
            return "errorPage";
        }
        location.setName(newName);
        location.setCountry(newCountry);
        locationService.save(location);
        List<GeneralWeather> generalWeathers = generalWeatherService.findAllByName(oldName);
        for (GeneralWeather generalWeather : generalWeathers){
            generalWeather.setName(newName);
            generalWeatherService.save(generalWeather);
        }
        return "redirect:/editor/toEditorPage";

    }

    @PostMapping("/updateDate")
    public String updateDate(@RequestParam("oldDate") String oldDate, @RequestParam("newPreviousDate") String newPreviousDate,
                             @RequestParam("newDate") String newDate, @RequestParam("newNextDate") String newNextDate){
        oldDate = oldDate.toUpperCase();
        newPreviousDate = newPreviousDate.toUpperCase();
        newDate = newDate.toUpperCase();
        newNextDate = newNextDate.toUpperCase();
        GeneralDate generalDate = generalDateService.findByDate(oldDate);
        if(generalDate == null){
            return "errorPage";
        }
        generalDate.setDate(newDate);
        generalDate.setNextDate(newNextDate);
        generalDate.setPreviousDate(newPreviousDate);
        List<GeneralWeather> generalWeathers = generalWeatherService.findAllByDate(oldDate);
        for (GeneralWeather generalWeather : generalWeathers){
            generalWeather.setDate(newDate);
            generalWeatherService.save(generalWeather);

        }
        return "redirect:/editor/toEditorPage";

    }
}