package com.shop.controller;

import com.shop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    @ResponseBody
    public String welcome() {
        return "<h1 style=\"width: 300px; margin: 0 auto; margin-top: 45vh;\">Welcome to CameraShop</h1>";
    }

    @GetMapping(value = "/email")
    public void sendMail() {
        try {
            emailService.sendVerificationEmail("dthoai2k3@gmail.com", "982734");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
