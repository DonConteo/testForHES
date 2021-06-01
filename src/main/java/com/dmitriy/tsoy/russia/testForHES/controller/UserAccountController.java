package com.dmitriy.tsoy.russia.testForHES.controller;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.service.UserAccountService;
import com.dmitriy.tsoy.russia.testForHES.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


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

    @GetMapping("new")
    public ModelAndView saveNewUserAccount() {
        ModelAndView modelAndView = new ModelAndView();
        UserAccount userAccount = new UserAccount();
        modelAndView.addObject("user_account", userAccount);
        modelAndView.setViewName("new");
        return modelAndView;
    }

    @PostMapping("new")
    public ModelAndView saveNewUserAccount(@Valid @ModelAttribute(value="user_account") UserAccount user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();
        if (result.hasErrors()) {
            modelAndView.setViewName("new");
        } else {
            userService.saveUser(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname());
            modelAndView.addObject("successMessage", "User has been added successfully");
            modelAndView.setViewName("redirect:/user");
        }
        return modelAndView;
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
