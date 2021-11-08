package com.example.demo.mail;

import com.example.demo.payload.request.DoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    //Send Welcome Email
    @Async
    public void sendEmail(DoctorRequest doctorRequest){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(doctorRequest.getEmail());
        msg.setSubject("Welcome to CRUD HOSPITAL");
        msg.setText("Hello Doctor "+doctorRequest.getFirstName()+" "+doctorRequest.getLastName()+
                "\n Happy to have you on board");
        javaMailSender.send(msg);
    }
}
