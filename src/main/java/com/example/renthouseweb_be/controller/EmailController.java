package com.example.renthouseweb_be.controller;

import com.example.renthouseweb_be.model.EmailDetails;
import com.example.renthouseweb_be.model.account.User;
import com.example.renthouseweb_be.service.EmailService;
import com.example.renthouseweb_be.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/convenient")
@CrossOrigin("*")
public class EmailController {
    private final EmailService emailService;
    private  final UserService userService;
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }


    @PostMapping("/sendMailWithTemplate")
    public String sendHtmlEmail(@RequestBody EmailDetails details) {
        Context context = new Context();

        emailService.sendMailWithTemplate(details, "verify-template", context);
        return "HTML email sent successfully!";
    }

    @PostMapping("/send-verification")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody EmailDetails details) {
        User user = userService.findByEmail(details.getRecipient());
        if (user != null) {
            Context context = new Context();
            String verificationToken = userService.generateVerificationToken(user);
            String verificationLink = "http://localhost:8080/verify?token=" + verificationToken;
            String emailContent = "Please click on the following link to verify your email:\n" + verificationLink;
            context.setVariable("content", emailContent);
            context.setVariable("verify-link", verificationLink);
            emailService.sendMailWithTemplate(details, "verify-template", context);
            return ResponseEntity.ok("Verification email sent successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }


}
