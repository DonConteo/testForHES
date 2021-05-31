package com.dmitriy.tsoy.russia.testForHES.service;

import com.dmitriy.tsoy.russia.testForHES.model.Role;
import com.dmitriy.tsoy.russia.testForHES.model.UserAccount;
import com.dmitriy.tsoy.russia.testForHES.repository.RoleRepository;
import com.dmitriy.tsoy.russia.testForHES.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserAccount> getAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    public void saveUser(String username, String password, String firstname, String lastname) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username);
        userAccount.setPassword(passwordEncoder.encode(password));
        userAccount.setFirstname(firstname);
        userAccount.setLastname(lastname);
        userAccount.setRoles(Collections.singleton(new Role(1L, "ADMIN")));
        userAccount.setStatus(true);
        userAccount.setCreateDate(LocalDate.now());
        userAccountRepository.save(userAccount);
    }

    public UserAccount getUserAccountById(long id) {
        return userAccountRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAccountRepository.findByUsername(username);
    }
}
