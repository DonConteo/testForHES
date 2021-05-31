package com.dmitriy.tsoy.russia.testForHES.controller;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserAccountController {

    @Autowired
    UserAccountService userAccountService;

    @GetMapping()
    public String getAllUserAccounts(Model model) {
        List<UserAccount> userAccounts = userAccountService.getAllUserAccounts();
        model.addAttribute("userAccounts", userAccounts);
        return "user";
    }

//    @GetMapping("{id}")
//    public String getUserAccountById(@PathVariable(value = "id") long id, Model model) {
//        UserAccount userAccount = userAccountService.getUserAccountById(id);
//        model.addAttribute("userAccount", userAccount);
//        return "user/{id}";
//    }
}
