package com.dmitriy.tsoy.russia.testForHES.controller;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.service.UserAccountService;
import com.dmitriy.tsoy.russia.testForHES.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserAccountController {

    @Autowired
    UserAccountService userService;
    @Autowired
    ValidatorService validatorService;

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("title", "Users");
        model.addAttribute("users", userService.findAllUsers());
        return "user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("new")
    public String saveNewUserAccount(Model model) {
        model.addAttribute("title", "Save User");
        return "new";
    }

    @PostMapping("new")
    public String saveNewUserAccount(String username, String password, String firstname, String lastname, Model model) {
        Map<String, List<String>> errors = userService.validateUserInfo(username, password, firstname, lastname);
        if(!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "new";
        }
        userService.saveUser(username, password, firstname, lastname);
        return "redirect:/user";
    }

    @GetMapping("{id}")
    public String getUserDetailsById(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Details");
        model.addAttribute("user", userService.getUserDetailsById(id));
        return "details";
    }

    @PostMapping("{id}")
    public String changeUserActivity(@PathVariable("id") long id, boolean status) {
        userService.changeUserActivity(id, status);
        return "redirect:/user/{id}";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{id}/edit")
    public String updateUserAccount(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Update");
        model.addAttribute("user", userService.getUserDetailsById(id));
        return "edit";
    }

    @PutMapping("{id}/edit")
    public String updateUserAccount(@PathVariable("id") long id, String username, String password, String firstname, String lastname) {
        userService.updateUser(id, username, password, firstname, lastname);
        return "redirect:/user";
    }
}
