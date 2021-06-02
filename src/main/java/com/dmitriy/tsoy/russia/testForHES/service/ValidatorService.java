package com.dmitriy.tsoy.russia.testForHES.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    @Autowired
    UserAccountService userAccountService;

    public boolean isUsernameOccupied(String username) {
        boolean flag = true;
        if(userAccountService.getUserAccountByUsername(username) == null) {
            flag = false;
        }
        return flag;
    }

    public boolean isUsernameLengthIsRight(String username) {
        boolean flag = true;
        if(username.length() < 2 || username.length() > 15) {
            flag = false;
        }
        return flag;
    }

    public boolean isUsernameLetters(String username) {
        boolean flag = true;
        for (char c : username.toCharArray()) {
            if (!(Character.isLetter(c))) {
                flag = false;
            }
        }
        return flag;
    }

    public boolean isPasswordLengthIsRight(String password) {
        boolean flag = true;
        if(password.length() < 2 || password.length() > 15) {
            flag = false;
        }
        return flag;
    }

    public boolean isPasswordLettersDigits(String password) {
        boolean flag = true;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                flag = false;
            }
        }
        return flag;
    }

    public boolean isPasswordContainsDigitsAndLetters(String password) {
        boolean mainFlag = true;
        boolean digitFlag = false;
        boolean letterFlag = false;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                letterFlag = true;
            } else {
                digitFlag = true;
            }
        }
        if(!(digitFlag && letterFlag)) {
            mainFlag = false;
        }
        return mainFlag;
    }

    public boolean isNameEmpty(String name) {
        boolean flag = false;
        if(name.equals("")) {
            flag = true;
        }
        return flag;
    }

    public boolean isNameLengthRight(String name) {
        boolean flag = true;
        if(name.length() > 15) {
            flag = false;
        }
        return flag;
    }

    public boolean isNameLetters(String name) {
        boolean flag = true;
        for (char c : name.toCharArray()) {
            if (!(Character.isLetter(c))) {
                flag = false;
            }
        }
        return flag;
    }
}
