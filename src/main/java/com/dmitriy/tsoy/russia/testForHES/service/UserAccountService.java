package com.dmitriy.tsoy.russia.testForHES.service;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    UserAccountRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ValidatorService validatorService;

    public void saveUser(String username, String password, String firstname, String lastname){
        UserAccount user = UserAccount.newBuilder().
                username(username).
                password(passwordEncoder.encode(password)).
                firstname(firstname).
                lastname(lastname).
                roles(Collections.singleton(new Role(1L, "ADMIN"))).
                createDate(LocalDate.now()).build();
        userRepo.save(user);
    }

    public void updateUser(long id, String username, String password, String firstname, String lastname) {
        userRepo.updateUserAccount(id, username, password, firstname, lastname);
    }

    public List<UserAccount> findAllUsers() {
        return userRepo.findAll();
    }

    public UserAccount getUserDetailsById(long id) {
        return userRepo.getById(id);
    }

    public void changeUserActivity(long id, boolean status) {
        userRepo.changeUserActivity(id, status);
    }

    public Map<String, List<String>> validateUserInfo(String username, String password, String firstname, String lastname) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> usernameErrors = validatorService.usernameValidate(username);
        List<String> passwordErrors = validatorService.passwordValidate(password);
        List<String> firstnameErrors = validatorService.nameValidate(firstname);
        List<String> lastnameErrors = validatorService.nameValidate(lastname);
        if(!usernameErrors.isEmpty()) {
            errors.put("usernameError", usernameErrors);
        }
        if(!passwordErrors.isEmpty()) {
            errors.put("passwordError", passwordErrors);
        }
        if(!firstnameErrors.isEmpty()) {
            errors.put("firstnameError", firstnameErrors);
        }
        if(!lastnameErrors.isEmpty()) {
            errors.put("lastnameError", lastnameErrors);
        }
        return errors;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}