package com.example.renthouseweb_be.service;

import com.example.renthouseweb_be.model.EmailDetails;
import org.thymeleaf.context.Context;
public interface EmailService {
    // To send a simple email
    String sendSimpleMail(EmailDetails details);

    // To send an email with attachment
    String sendMailWithAttachment(EmailDetails details);
    String sendMailWithTemplate(EmailDetails details, String template, Context context);
}
