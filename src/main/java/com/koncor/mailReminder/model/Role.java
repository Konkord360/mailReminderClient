package com.koncor.mailReminder.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @Column(name = "role")
    private String name;

//    @Column(name = "username")
//    private String username;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "username")
    private User user;

//    @Column(name = "username")
//    private String username;

    public Role(String name){
        this.name = name;
    }

    public Role(User user, String name){
        this.id = 0L;
        this.user = user;
//        this.username = user.getUsername();
        this.name = name;
    }

    public Role(){
        this.id = 0L;
        this.user = null;
        this.name = null;
    }

//    public String getUsername() {
//        return username;
//    }

//    public Role(String name, String username){
//        this.name = name;
////        this.username = username;
//    }

//    public void setUsername(String username) {
//        this.username = username;
//    }
//    @Column(name = "user_login")
//    private String username;
//
//    @JoinColumn(name = "username")


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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name +
                "', User['" + user.toString() + "'}";
    }
}
