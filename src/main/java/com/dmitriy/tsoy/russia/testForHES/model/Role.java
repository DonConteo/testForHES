package com.dmitriy.tsoy.russia.testForHES.model;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<UserAccount> users;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<UserAccount> getUsers() {
        return users;
    }
    public void setUsers(Set<UserAccount> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}