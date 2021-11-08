package com.example.demo.security.service;


import com.example.demo.model.Nurse;
import com.example.demo.payload.request.DoctorRequest;
import com.example.demo.payload.request.NurseRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.NurseRepo;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseRepo nurseRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    UserRepo userRepo;

    //Send Welcome Email
    private void sendEmail(NurseRequest nurseRequest){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(nurseRequest.getEmail());

        msg.setSubject("Welcome to CRUD HOSPITAL");
        msg.setText("Hello Doctor "+nurseRequest.getFirstName()+" "+nurseRequest.getLastName()+
                "\n Happy to have you on board");

        javaMailSender.send(msg);
    }

    //Add Doctors
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> createNurse(NurseRequest nurseRequest) {
        if (nurseRepo.existsByUsername( nurseRequest.getUsername())  || userRepo.existsByUsername(nurseRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (nurseRepo.existsByEmail(nurseRequest.getEmail()) || userRepo.existsByEmail(nurseRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Nurse newNurse = new Nurse();
        newNurse.setUsername(nurseRequest.getUsername());
        newNurse.setFirstName(nurseRequest.getFirstName());
        newNurse.setLastName(nurseRequest.getLastName());
        newNurse.setAddress(nurseRequest.getAddress());
        newNurse.setDateOfBirth(nurseRequest.getDateOfBirth());
        newNurse.setSpecialty(nurseRequest.getSpecialty());
        newNurse.setPhoneNumber(nurseRequest.getPhoneNumber());
        newNurse.setEmail(nurseRequest.getEmail());
        newNurse.setPassword(encoder.encode(nurseRequest.getPassword()));
        nurseRepo.save(newNurse);
        sendEmail(nurseRequest);
        return ResponseEntity.ok().body(new MessageResponse("New NurseCreated Successfully"));
    }

    //Get all Doctors
    @Transactional
    @Override
    public List<Nurse> getNurses() {
        return nurseRepo.findAll();
    }

    //Get one doctor by id
    @Transactional
    @Override
    public Optional<Nurse> getNurse(@PathVariable Long id) {
        return nurseRepo.findById(id);
    }

    //Update a doctor
    @Transactional
    public Optional<Nurse> updateNurse(Long id, NurseRequest nurseRequest) {
        Optional<Nurse> nurseFromDatabase = nurseRepo.findById(id);
        nurseFromDatabase.get().setFirstName(nurseRequest.getFirstName());
        nurseFromDatabase.get().setLastName(nurseRequest.getLastName());
        nurseFromDatabase.get().setAddress(nurseRequest.getAddress());
        nurseFromDatabase.get().setSpecialty(nurseRequest.getSpecialty());
        nurseFromDatabase.get().setPhoneNumber(nurseRequest.getPhoneNumber());
        return nurseFromDatabase;
    }

    //Delete a nurse
    @Transactional
    @Override
    public MessageResponse deleteNurse(@PathVariable Long id) {
        Nurse nurseFromDatabase = nurseRepo.findById(id).get();
        nurseRepo.delete(nurseFromDatabase);
        return new MessageResponse("Doctor deleted successfully");
    }
}
