package com.koncor.mailReminder.model;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "user")
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(){
        this.password = null;
        this.username = null;
        this.id = 0L;
        this.passwordConfirm = null;
        this.roles = null;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username +
                "', password='" + password + "'}";
    }
}
