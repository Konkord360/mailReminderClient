package com.koncor.mailReminder.services;

import com.koncor.mailReminder.model.Role;
import com.koncor.mailReminder.model.User;
import com.koncor.mailReminder.repository.RoleRepository;
import com.koncor.mailReminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
//        Role role = new Role();
//
//        role.setId(0L);
//        role.setName("ROLE_USER");
////        role.setUsername(user.getUsername());
//        role.setUsers(user);


        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = new Role(user, "ROLE_USER");
        System.out.println(role.toString());
        user.setRoles(new HashSet<>(Collections.singletonList(role)));//roleRepository.findAll()));
        user.setId(0L);
        System.out.println(user.toString());


//        roleRepository.save(role);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}