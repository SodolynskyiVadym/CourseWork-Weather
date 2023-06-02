package com.code.CourseWorkWeather.controllers;


import com.code.CourseWorkWeather.models.User;
import com.code.CourseWorkWeather.models.UserRegisterResponse;
import com.code.CourseWorkWeather.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/toMainAdminPage")
    public String teAdminPage(Model model){
        model.addAttribute("usersList", userService.findAll());
        return "adminPage";
    }
    @GetMapping("/toRegistrationForm")
    public String registrationForm(Model model){
        model.addAttribute("userRegisterResponse", new UserRegisterResponse());
        return "register";
    }

    @PostMapping("/deleteUser")
    public String deleteUserByUsername(@RequestParam("username") String username){
        userService.deleteByUsername(username);
        return "redirect:/admin/toMainAdminPage";
    }
}
