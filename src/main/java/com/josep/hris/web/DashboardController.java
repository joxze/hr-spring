package com.josep.hris.web;

import com.josep.hris.entity.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    @GetMapping("")
    public String index(Principal principal, Authentication auth)
    {
        System.out.println(principal.getName());
        System.out.println((Users)auth.getPrincipal());
        System.out.println(auth.getDetails());

        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("User has authorities: " + user.getStatus());
        return "dashboard/index";
    }
}
