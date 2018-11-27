package com.josep.hris.web;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {
    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;


    @GetMapping("/registration")
    public String registration(Model model)
    {
        RegistrationForm users = new RegistrationForm();
        model.addAttribute("users", users);
        return "users/registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("users") RegistrationForm users, Model model, BindingResult result)
    {
        validator.validate(users, result);
        userService.save(users);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout)
    {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "users/login";
    }
}
