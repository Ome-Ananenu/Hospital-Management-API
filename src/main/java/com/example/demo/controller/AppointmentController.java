package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    //Add Appointment
    @PostMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> createAppointment(@Valid @RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointment), HttpStatus.OK);
    }

    //Get all appointments
    @GetMapping
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getAppointments() {
        List<Appointment> appointments =  appointmentService.getAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    //Get an appointment by id
    @GetMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<Appointment> getAppointment(@PathVariable Long id) {
        return new ResponseEntity(appointmentService.getAppointment(id), HttpStatus.OK);
    }

    //Get all appointments linked to a patient
    @GetMapping(path = "/patient/{patientId}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable("patientId") Long patientId){
           return new ResponseEntity<List<Appointment>>(appointmentService.getPatientAppointment(patientId), HttpStatus.OK);
    }

    //Get all appointments linked to a doctor
    @GetMapping(path = "/doctor/{doctorId}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable("doctorId") Long doctorId){
        return new ResponseEntity<List<Appointment>>(appointmentService.getDoctorAppointment(doctorId), HttpStatus.OK);
    }

    //Get all appointments linked to a doctor
    @GetMapping(path = "/nurse/{nurseId}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Appointment>> getNurseAppointments(@PathVariable("nurseId") Long nurseId){
        return new ResponseEntity<List<Appointment>>(appointmentService.getNurseAppointment(nurseId), HttpStatus.OK);
    }

    //Update an appointment
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateAppointment(@PathVariable Long id,@RequestBody Appointment appointment) {
        MessageResponse updateAppointment = appointmentService.updateAppointment(id,appointment);
        return new ResponseEntity<>(updateAppointment, HttpStatus.OK);
    }

    //Delete an appointment
    @DeleteMapping(path="/{id}")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>("Appointment with ID :" + id + " deleted successfully", HttpStatus.OK);
    }

}
