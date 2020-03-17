package com.koncor.mailReminder.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
