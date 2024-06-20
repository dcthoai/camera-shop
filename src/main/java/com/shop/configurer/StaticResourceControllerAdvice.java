package com.shop.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class StaticResourceControllerAdvice {

    @Value("${request.url}")
    private String staticResourceUrl;

    @ModelAttribute
    public void addStaticResourceUrl(Model model) {
        model.addAttribute("staticUrl", staticResourceUrl);
    }
}