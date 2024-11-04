package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.model.EmailDetails;
import com.example.renthouseweb_be.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
public class EmailController {
    private final EmailService emailService;
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // Sending simple email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        return emailService.sendSimpleMail(details);
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody EmailDetails details) {
        return emailService.sendMailWithAttachment(details);
    }

    @PostMapping("/sendMailWithTemplate")
    public String sendHtmlEmail(@RequestBody EmailDetails details) {
        Context context = new Context();
        context.setVariable("message", details.getMsgBody());

        emailService.sendMailWithTemplate(details, "verify-template", context);
        return "HTML email sent successfully!";
    }


}
