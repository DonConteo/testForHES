package com.dmitriy.tsoy.russia.testForHES.controller;

import com.dmitriy.tsoy.russia.testForHES.dto.UserDto;
import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.service.UserAccountService;
import com.dmitriy.tsoy.russia.testForHES.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping("user")
public class UserAccountController {

    @Autowired
    UserAccountService userService;
    @Autowired
    ValidatorService validatorService;

    @GetMapping()
    public ModelAndView getAllUsers(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView();
        Page<UserAccount> page;

        page = userService.findAllUsers(pageable);

        int totalPages = page.getTotalPages();
        if(totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pages", pageNumbers);
        }
        modelAndView.addObject("users", page);
        modelAndView.setViewName("user");
        return modelAndView;
    }

    @GetMapping("{id}")
    public ModelAndView getUserDetailsById(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("details");
        modelAndView.addObject("user", userService.getUserDetailsById(id));
        return modelAndView;
    }

    @PostMapping("{id}")
    public ModelAndView changeUserActivity(@PathVariable("id") long id, boolean status) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/{id}");
        userService.changeUserActivity(id, status);
        return modelAndView;

//        return "redirect:/user/{id}";
    }

    @GetMapping("new")
    public ModelAndView saveNewUserAccount() {
        ModelAndView modelAndView = new ModelAndView();
        UserDto userDto = new UserDto();
        modelAndView.addObject("userdto", userDto);
        modelAndView.setViewName("new");
        return modelAndView;
    }

    @PostMapping("new")
    public ModelAndView saveNewUserAccount(@ModelAttribute(value="userdto") UserDto userDto, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView();

        if(!validatorService.isUsernameLengthIsRight(userDto.getUsername())) {
            result.
                    rejectValue("username", "error.username.length",
                            "Username length must be 3-16 symbols");
        }
        if(validatorService.isUsernameOccupied(userDto.getUsername())) {
            result.
                    rejectValue("username", "error.username.unique",
                            "Username is occupied");
        }
        if(!validatorService.isUsernameLetters(userDto.getUsername())) {
            result.
                    rejectValue("username", "error.username.letters",
                            "Username must contain latin letters only");
        }

        if(!validatorService.isPasswordLengthIsRight(userDto.getPassword())) {
            result.
                    rejectValue("password", "error.password.length",
                            "Password length must be 3-16 symbols");
        }
        if(!validatorService.isPasswordLettersDigits(userDto.getPassword())) {
            result.
                    rejectValue("password", "error.password.symbols",
                            "Password must contain latin letters and numbers only");
        }
        if(!validatorService.isPasswordContainsDigitsAndLetters(userDto.getPassword())) {
            result.
                    rejectValue("password", "error.password.digitOrLetter",
                            "Password must contain at least one digit and one letter");
        }

        if(validatorService.isNameEmpty(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.null",
                            "Firstname can't be empty");
        }
        if(!validatorService.isNameLengthRight(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.length",
                            "Firstname length must be 1-16 symbols");
        }
        if(!validatorService.isNameLetters(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.letters",
                            "Firstname must contain latin letters only");
        }

        if(validatorService.isNameEmpty(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.null",
                            "Lastname can't be empty");
        }
        if(!validatorService.isNameLengthRight(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.length",
                            "Lastname length must be 1-16 symbols");
        }
        if(!validatorService.isNameLetters(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.letters",
                            "Lastname must contain latin letters only");
        }

        if(result.hasErrors()){
            modelAndView.setViewName("new");
            return modelAndView;
        }
        userService.saveUser(userDto.getUsername(), userDto.getPassword(), userDto.getFirstname(), userDto.getLastname());
        modelAndView.setViewName("redirect:/user");
        return modelAndView;
        }

    @GetMapping("{id}/edit")
    public ModelAndView updateUserAccount(@PathVariable("id") long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("edit");
        UserAccount user = userService.getUserDetailsById(id);
        UserDto userDto = new UserDto(user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname());
        modelAndView.addObject("userdto", userDto);
        return modelAndView;
    }

    @PostMapping("{id}/edit")
    public ModelAndView updateUserAccount(@PathVariable("id") long id, @ModelAttribute(value="userdto") UserDto userDto, BindingResult result ) {
        ModelAndView modelAndView = new ModelAndView();
        UserDto userForUpdate = new UserDto();

        if(userDto.getUsername().equals(userService.getUserDetailsById(id).getUsername())) {
            userForUpdate.setUsername(userDto.getUsername());
        } else {
            if(validatorService.isUsernameOccupied(userDto.getUsername())) {
                result.
                        rejectValue("username", "error.username.unique",
                                "Username is occupied");
            }
            if(!validatorService.isUsernameLengthIsRight(userDto.getUsername())) {
                result.
                        rejectValue("username", "error.username.length",
                                "Username length must be 3-16 symbols");
            }
            if(!validatorService.isUsernameLetters(userDto.getUsername())) {
                result.
                        rejectValue("username", "error.username.letters",
                                "Username must contain latin letters only");
            }
        }

        if(userDto.getPassword().equals("")) {
            userForUpdate.setPassword("");
        } else {
            if(!validatorService.isPasswordLengthIsRight(userDto.getPassword())) {
                result.
                        rejectValue("password", "error.password.length",
                                "Password length must be 3-16 symbols");
            }
            if(!validatorService.isPasswordLettersDigits(userDto.getPassword())) {
                result.
                        rejectValue("password", "error.password.symbols",
                                "Password must contain latin letters and numbers only");
            }
            if(!validatorService.isPasswordContainsDigitsAndLetters(userDto.getPassword())) {
                result.
                        rejectValue("password", "error.password.digitOrLetter",
                                "Password must contain at least one digit and one letter");
            }
        }

        if(validatorService.isNameEmpty(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.null",
                            "Firstname can't be empty");
        }
        if(!validatorService.isNameLengthRight(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.length",
                            "Firstname length must be 1-16 symbols");
        }
        if(!validatorService.isNameLetters(userDto.getFirstname())) {
            result.
                    rejectValue("firstname", "error.firstname.letters",
                            "Firstname must contain latin letters only");
        }

        if(validatorService.isNameEmpty(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.null",
                            "Lastname can't be empty");
        }
        if(!validatorService.isNameLengthRight(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.length",
                            "Lastname length must be 1-16 symbols");
        }
        if(!validatorService.isNameLetters(userDto.getLastname())) {
            result.
                    rejectValue("lastname", "error.lastname.letters",
                            "Lastname must contain latin letters only");
        }

        if(result.hasErrors()){
            modelAndView.setViewName("edit");
            return modelAndView;
        }
        if(!userForUpdate.getPassword().equals("")) {
            userForUpdate.setPassword(userDto.getPassword());
        }
        userForUpdate.setFirstname(userDto.getFirstname());
        userForUpdate.setLastname(userDto.getLastname());

        if(userForUpdate.getPassword().equals("")) {
            userService.updateUser(id, userForUpdate.getUsername(), userForUpdate.getFirstname(), userForUpdate.getLastname());
        } else {
            userService.updateUser(id, userForUpdate.getUsername(), userForUpdate.getPassword(), userForUpdate.getFirstname(), userForUpdate.getLastname());
        }
        modelAndView.setViewName("redirect:/user/{id}");
        return modelAndView;
    }
}
