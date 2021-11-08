package com.example.demo.security.service;


import com.example.demo.model.Nurse;
import com.example.demo.payload.request.NurseRequest;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface NurseService {
    ResponseEntity<MessageResponse> createNurse(NurseRequest nurse);

    List<Nurse> getNurses();

    Optional<Nurse> getNurse(Long id);

    Optional<Nurse> updateNurse(Long id, NurseRequest nurseRequest);

    MessageResponse deleteNurse(Long id);
}
