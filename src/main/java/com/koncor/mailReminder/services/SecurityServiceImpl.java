package com.koncor.mailReminder.services;

import com.koncor.mailReminder.accessDataJPA.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager, MyUserDetailsService myUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (auth.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }
}
