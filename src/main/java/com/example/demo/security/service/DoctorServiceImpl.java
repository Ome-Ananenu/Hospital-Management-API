package com.example.demo.security.service;

import com.example.demo.mail.EmailSender;
import com.example.demo.model.Doctor;
import com.example.demo.payload.request.DoctorRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.DoctorRepo;

import com.example.demo.repository.UserRepo;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailSender emailSender;

    @Autowired
    UserRepo userRepo;


    //Add Doctors
    @Transactional
    @Override
    public ResponseEntity<MessageResponse> createDoctor(DoctorRequest doctorRequest) {
        if (doctorRepo.existsByUsername( doctorRequest.getUsername()) || userRepo.existsByUsername( doctorRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (doctorRepo.existsByEmail(doctorRequest.getEmail()) || userRepo.existsByEmail(doctorRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Doctor newDoctor = new Doctor();
        newDoctor.setUsername(doctorRequest.getUsername());
        newDoctor.setFirstName(doctorRequest.getFirstName());
        newDoctor.setLastName(doctorRequest.getLastName());
        newDoctor.setAddress(doctorRequest.getAddress());
        newDoctor.setDateOfBirth(doctorRequest.getDateOfBirth());
        newDoctor.setSpecialty(doctorRequest.getSpecialty());
        newDoctor.setPhoneNumber(doctorRequest.getPhoneNumber());
        newDoctor.setEmail(doctorRequest.getEmail());
        newDoctor.setPassword(encoder.encode(doctorRequest.getPassword()));
        doctorRepo.save(newDoctor);
        emailSender.sendEmail(doctorRequest);
        return ResponseEntity.ok().body(new MessageResponse("New Doctor Created Successfully"));
    }

    //Get all Doctors
    @Transactional
    @Override
    public List<Doctor> getDoctors() {
        return doctorRepo.findAll();
    }

    //Get one doctor by id
    @Transactional
    @Override
    public Optional<Doctor> getDoctor(@PathVariable Long id) {
        return doctorRepo.findById(id);
    }

    //Update a doctor
    @Transactional
    public Optional<Doctor> updateDoctor(Long id, DoctorRequest doctorRequest) {
        Optional<Doctor> doctorFromDatabase = doctorRepo.findById(id);
        doctorFromDatabase.get().setFirstName(doctorRequest.getFirstName());
        doctorFromDatabase.get().setLastName(doctorRequest.getLastName());
        doctorFromDatabase.get().setDateOfBirth(doctorRequest.getDateOfBirth());
        doctorFromDatabase.get().setEmail(doctorRequest.getEmail());
        doctorFromDatabase.get().setAddress(doctorRequest.getAddress());
        doctorFromDatabase.get().setSpecialty(doctorRequest.getSpecialty());
        doctorFromDatabase.get().setPhoneNumber(doctorRequest.getPhoneNumber());
        return doctorFromDatabase;
    }

    //Delete a doctor
    @Transactional
    @Override
    public MessageResponse deleteDoctor(@PathVariable Long id) {
        Doctor doctorFromDatabase = doctorRepo.findById(id).get();
        doctorRepo.delete(doctorFromDatabase);
        return new MessageResponse("Doctor deleted successfully");
    }
}
