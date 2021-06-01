package com.dmitriy.tsoy.russia.testForHES.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Size(min = 3, max = 16, message = "Username length must be 3-16 symbols")
    @Pattern(regexp = "[a-zA-Z]", message = "Username must contain latin letters only")
    private String username;
    @Size(min = 3, max = 16, message = "Password length must be 3-16 symbols")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Password must contain latin letters and numbers only")
    private String password;
    @Size(min = 1, max = 16, message = "Firstname length must be 1-16 symbols")
    @Pattern(regexp = "[a-zA-Z]", message = "Password must contain at least one letter")
    private String firstname;
    @Size(min = 1, max = 16, message = "Lastname length must be 1-16 symbols")
    @Pattern(regexp = "[a-zA-Z]", message = "Password must contain at least one digit")
    private String lastname;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
    private boolean status;
    private LocalDate createDate;

    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public LocalDate getCreateDate() {
        return createDate;
    }
    public long getId() {
        return id;
    }
    @Override
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    private Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isStatus();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserAccount.Builder newBuilder(){
        return new UserAccount().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public UserAccount.Builder username(String username){
            UserAccount.this.username = username;
            return this;
        }

        public UserAccount.Builder password(String password){
            UserAccount.this.password = password;
            return this;
        }

        public UserAccount.Builder firstname(String firstname) {
            UserAccount.this.firstname = firstname;
            return this;
        }

        public UserAccount.Builder lastname(String lastname) {
            UserAccount.this.lastname = lastname;
            return this;
        }

        public UserAccount.Builder roles(Set<Role> roles) {
            UserAccount.this.roles = roles;
            return this;
        }

        public UserAccount.Builder status(boolean status) {
            UserAccount.this.status = status;
            return this;
        }

        public UserAccount.Builder createDate(LocalDate createDate) {
            UserAccount.this.createDate = createDate;
            return this;
        }

        public UserAccount build() {
            return UserAccount.this;
        }
    }
}
