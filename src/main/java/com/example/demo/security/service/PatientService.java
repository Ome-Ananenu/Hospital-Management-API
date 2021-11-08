package com.example.demo.security.service;

import com.example.demo.model.Patient;
import com.example.demo.payload.request.PatientRequest;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface PatientService {
    ResponseEntity<MessageResponse> createPatient(PatientRequest patientrequest);

    List<Patient> getPatients();

    Optional<Patient> getPatient(Long id);

    MessageResponse updatePatient(Long id, PatientRequest patientRequest);

    MessageResponse deletePatient(Long id);
}
