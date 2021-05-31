package com.dmitriy.tsoy.russia.testForHES.controller;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import com.dmitriy.tsoy.russia.testForHES.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    UserAccountService userAccountService;

    @GetMapping()
    public String registration(){
        return "registration";
    }

    @PostMapping()
    public String saveUser(String username, String password, String firstname, String lastname){
        userAccountService.saveUser(username, password, firstname, lastname);
        return "redirect:/login";
    }
}