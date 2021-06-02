package com.dmitriy.tsoy.russia.testForHES.dto;

import java.time.LocalDate;
import java.util.Set;

public class UserDto {

    private long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Set<String> roles;
    private String status;
    private LocalDate createDate;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Set<String> getRoles() {
        return roles;
    }
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserDto() {
    }

    public UserDto(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserDto(long id, String username, String firstname, String lastname, Set<String> roles, String status, LocalDate createDate) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.roles = roles;
        this.status = status;
        this.createDate = createDate;
    }
}
