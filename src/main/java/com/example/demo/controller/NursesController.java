package com.example.demo.controller;


import com.example.demo.model.Nurse;
import com.example.demo.payload.request.NurseRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/nurse")
public class NursesController {

    @Autowired
    NurseService nurseService;

    //Add Nurses
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createNurse(@Valid @RequestBody NurseRequest nurse) {
        return new ResponseEntity(nurseService.createNurse(nurse), HttpStatus.OK);
    }

    //Get all Nurses
    @GetMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Nurse>> getNurses() {
        return new ResponseEntity<>(nurseService.getNurses(), HttpStatus.OK);
    }

    //Get one Nurse by id
    @GetMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Nurse> getNurse(@PathVariable Long id) {
        return new ResponseEntity(nurseService.getNurse(id), HttpStatus.OK);
    }

    //Update a Nurse
    @PutMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateNurse(@PathVariable Long id, @Valid @RequestBody NurseRequest nurse) {
        return new ResponseEntity(nurseService.updateNurse(id, nurse), HttpStatus.OK);
    }

    //Delete a Nurse
    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Nurse> deleteNurse(@PathVariable Long id) {
        nurseService.deleteNurse(id);
        return ResponseEntity.noContent().build();
    }
}
