package com.example.demo.security.service;

import com.example.demo.model.Doctor;
import com.example.demo.payload.request.DoctorRequest;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    ResponseEntity<MessageResponse> createDoctor(DoctorRequest doctor);

    List<Doctor> getDoctors();

    Optional<Doctor> getDoctor(Long id);

    Optional<Doctor> updateDoctor(Long id, DoctorRequest doctorRequest);

    MessageResponse deleteDoctor(Long id);
}
