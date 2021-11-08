package com.example.demo.mail;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {
    @Autowired
    MailService mailService;

    @RequestMapping(value = "/sendemail")
    public String send() throws AddressException, MessagingException, IOException {
        //sendEmail();
        mailService.sendEmail();
               //.sendEmailWithAttachment();
        return "Email sent successfully";
    }
}
