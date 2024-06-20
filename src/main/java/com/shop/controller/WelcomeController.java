package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class WelcomeController {

    @GetMapping
    @ResponseBody
    public String welcome() {
        return "<h1 style=\"width: 300px; margin: 0 auto; margin-top: 45vh;\">Welcome to CameraShop</h1>";
    }
}