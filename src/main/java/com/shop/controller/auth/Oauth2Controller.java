package com.shop.controller.auth;

import com.shop.security.oauth2.OAuth2GmailUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2Controller {

    @GetMapping(value = "/login")
    @ResponseBody
    public String login() {
        return "Login Page";
    }

    @GetMapping(value = "/admin")
    public String user() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2GmailUser user = (OAuth2GmailUser) auth.getPrincipal();

        return user.getAuthorities().toString();
    }

    @GetMapping(value = "/user")
    public String admin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        OAuth2GmailUser user = (OAuth2GmailUser) auth.getPrincipal();

        return user.getAuthorities().toString();
    }
}