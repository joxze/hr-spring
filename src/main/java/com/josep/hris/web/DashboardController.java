package com.josep.hris.web;

import com.josep.hris.entity.Users;
import com.josep.hris.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private AuthService auth;

    @GetMapping("")
    public String index(Model model, Principal principal)
    {
        model.addAttribute("auth", auth.getIdentity(principal));
        return "dashboard/index";
    }
}
