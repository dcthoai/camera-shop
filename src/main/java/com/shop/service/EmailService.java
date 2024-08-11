package com.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendVerificationEmail(String receiverEmail, String code) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        Context context = new Context();
        context.setVariable("receiverEmail", receiverEmail);
        context.setVariable("code", code);

        String htmlBody = templateEngine.process("email", context);

        helper.setTo(receiverEmail);
        helper.setSubject("Khôi phục tài khoản");
        helper.setText(htmlBody, true);

        emailSender.send(message);
    }
}
