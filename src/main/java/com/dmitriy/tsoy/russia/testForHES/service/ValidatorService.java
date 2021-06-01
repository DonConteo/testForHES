package com.dmitriy.tsoy.russia.testForHES.service;

import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidatorService {

    @Autowired
    UserAccountService userAccountService;

    public List<String> usernameValidate(String username) {
        List<String> usernameErrors = new ArrayList<>();
        List<UserAccount> userAccounts = userAccountService.findAllUsers();
        List<String> usernames = new ArrayList<>();
        for(UserAccount userAccount : userAccounts) {
            usernames.add(userAccount.getUsername());
        }
        if(usernames.contains(username)) {
            usernameErrors.add("Username is occupied");
        }
        if(username.length() < 2 || username.length() > 15) {
            usernameErrors.add("Username length must be 3-16 symbols");
        }
        while(!username.matches("[a-zA-Z]+")) {
            usernameErrors.add("Username must contain latin letters only");
        }
        return usernameErrors;
    }

    public List<String> passwordValidate(String password) {
        List<String> passwordErrors = new ArrayList<>();
        if(password.length() < 2 || password.length() > 15) {
            passwordErrors.add("Password length must be 3-16 symbols");
        }
        if(!password.matches("^[a-zA-Z0-9]+$")) {
            passwordErrors.add("Password must contain latin letters and numbers only");
        } else {
            char c = password.charAt(0);
            int letters = 0;
            int digits = 0;
            if(Character.isDigit(c)) {
                digits++;
            } else {
                letters++;
            }
            if(letters == 0) {
                passwordErrors.add("Password must contain at least one letter");
            }
            if(digits == 0) {
                passwordErrors.add("Password must contain at least one digit");
            }
        }
        return passwordErrors;
    }

    public List<String> nameValidate(String name) {
        List<String> nameError = new ArrayList<>();
        while (!name.matches("[a-zA-Z]+")) {
            nameError.add("Username must contain latin letters only");
        }
        return nameError;
    }
}
