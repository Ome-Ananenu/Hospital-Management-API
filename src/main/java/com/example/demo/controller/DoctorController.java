package com.example.demo.controller;

import com.example.demo.model.Doctor;
import com.example.demo.payload.request.DoctorRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    //Add Doctors
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createDoctor(@Valid @RequestBody DoctorRequest doctor) {
        return new ResponseEntity(doctorService.createDoctor(doctor), HttpStatus.OK);
    }

    //Get all Doctors
    @GetMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Doctor>> getDoctors() {
        return new ResponseEntity<>(doctorService.getDoctors(), HttpStatus.OK);
    }

    //Get one Doctor by id
    @GetMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Doctor> getDoctor(@PathVariable Long id) {
        return new ResponseEntity(doctorService.getDoctor(id), HttpStatus.OK);
    }

    //Update a Doctor
    @PutMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorRequest doctor) {
        return new ResponseEntity(doctorService.updateDoctor(id, doctor), HttpStatus.OK);
    }

    //Delete a Doctor
    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
