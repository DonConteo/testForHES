package com.dmitriy.tsoy.russia.testForHES.service;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public void saveUser(String username, String password, String firstname, String lastname){
        UserAccount user = UserAccount.newBuilder().
                username(username).
                password(passwordEncoder.encode(password)).
                firstname(firstname).
                lastname(lastname).
                roles(Collections.singleton(new Role(2L, "USER"))).
                status(true).
                createDate(LocalDate.now()).build();
        userRepo.save(user);
    }

    public UserAccount getUserAccountByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void updateUser(long id, String username, String password, String firstname, String lastname) {
        userRepo.updateUserAccount(id, username, passwordEncoder.encode(password), firstname, lastname);
    }

    public void updateUser(long id, String username, String firstname, String lastname) {
        userRepo.updateUserAccountWithoutPassword(id, username, firstname, lastname);
    }

    public Page<UserAccount> findAllUsers(Pageable pageable) {
        return userRepo.findAllUsers(pageable);
    }

    public UserAccount getUserDetailsById(long id) {
        return userRepo.getById(id);
    }

    public void changeUserActivity(long id, boolean status) {
        userRepo.changeUserActivity(id, status);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}