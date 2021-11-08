package com.example.demo.security.service;

import com.example.demo.model.Patient;
import com.example.demo.payload.request.DoctorRequest;
import com.example.demo.payload.request.PatientRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.PatientRepo;
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
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepo userRepo;

    //Send Welcome Email
    private void sendEmail(PatientRequest patientRequest){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(patientRequest.getEmail());

        msg.setSubject("Welcome to CRUD HOSPITAL");
        msg.setText("Hello "+patientRequest.getFirstName()+" "+patientRequest.getLastName()+
                "\n Your health is our priority");

        javaMailSender.send(msg);
    }


    //Add Patients
//    @Transactional
    @Override
    public ResponseEntity<MessageResponse> createPatient(PatientRequest patientRequest) {
        if (patientRepo.existsByUsername( patientRequest.getUsername())|| userRepo.existsByUsername( patientRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (patientRepo.existsByEmail(patientRequest.getEmail()) || userRepo.existsByEmail(patientRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Patient newPatient = new Patient();
        newPatient.setFirstName(patientRequest.getFirstName());
        newPatient.setLastName(patientRequest.getLastName());
        newPatient.setAddress(patientRequest.getAddress());
        newPatient.setDateOfBirth(patientRequest.getDateOfBirth());
        newPatient.setPhoneNumber(patientRequest.getPhoneNumber());
        newPatient.setOccupation(patientRequest.getOccupation());
        newPatient.setEmail(patientRequest.getEmail());
        newPatient.setPassword(encoder.encode(patientRequest.getPassword()));

        patientRepo.save(newPatient);
        sendEmail(patientRequest);
        return ResponseEntity.ok().body(new MessageResponse("New Nurse Created Successfully"));
    }

    //Get all Patients
    @Transactional
    @Override
    public List<Patient> getPatients() {
        return patientRepo.findAll();
    }

    //Get one patient by id
    @Transactional
    @Override
    public Optional<Patient> getPatient(@PathVariable Long id) {
        return patientRepo.findById(id);
    }

    //Update a patient
    @Transactional
    @Override
    public MessageResponse updatePatient(Long id, PatientRequest patientRequest) {
        Optional<Patient> patientFromDatabase = patientRepo.findById(id);
        if (patientFromDatabase.isEmpty()){
           return new MessageResponse("Patient doesn't exist");
        }
        patientFromDatabase.get().setFirstName(patientRequest.getFirstName());
        patientFromDatabase.get().setLastName(patientRequest.getLastName());
        patientFromDatabase.get().setAddress(patientRequest.getAddress());
        patientFromDatabase.get().setDateOfBirth(patientRequest.getDateOfBirth());
        patientFromDatabase.get().setPhoneNumber(patientRequest.getPhoneNumber());
        patientFromDatabase.get().setOccupation(patientRequest.getOccupation());
//        patientFromDatabase.get().setAppointments(patientRequest.getAppointments());
        patientRepo.save(patientFromDatabase.get());
        return new MessageResponse("Patient updated");
    }


    //

    //Delete a patient
    @Transactional
    @Override
    public MessageResponse deletePatient(@PathVariable Long id) {
        Patient patientFromDatabase = patientRepo.findById(id).get();
        patientRepo.delete(patientFromDatabase);
        return new MessageResponse("Patient removed successfully");
    }
}