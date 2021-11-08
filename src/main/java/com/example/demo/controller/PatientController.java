package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.payload.request.PatientRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    //Add Patients
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
    public ResponseEntity<MessageResponse> createPatient(@Valid @RequestBody PatientRequest patient) {
        return new ResponseEntity(patientService.createPatient(patient), HttpStatus.OK);
    }

    //Get all Patients
    @GetMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
    public ResponseEntity<List<Patient>> getPatients() {
        List<Patient> patients = patientService.getPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

   //Get one patient by id
   @GetMapping(path="/{id}")
   @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
   public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
       return new ResponseEntity(patientService.getPatient(id), HttpStatus.OK);
   }

   //Update a patient
    @PutMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
    public ResponseEntity<MessageResponse> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequest patient) {
        MessageResponse updatePatient = patientService.updatePatient(id, patient);
        return new ResponseEntity(updatePatient, HttpStatus.OK);
    }

    //Delete a patient
    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN') or hasRole('NURSE')")
    public ResponseEntity<Patient> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
