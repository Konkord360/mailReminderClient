package com.koncor.mailReminder.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @Column(name = "role")
    private String name;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "username")
    private User user;

    public Role(String name){
        this.name = name;
    }

    public Role(User user, String name){
        this.id = 0L;
        this.user = user;
        this.name = name;
    }

    public Role(){
        this.id = 0L;
        this.user = null;
        this.name = null;
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

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name +
                "', User['" + user.toString() + "'}";
    }
}
