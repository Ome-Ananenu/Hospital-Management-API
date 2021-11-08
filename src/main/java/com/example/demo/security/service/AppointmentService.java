package com.example.demo.security.service;


import com.example.demo.model.Appointment;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AppointmentService {
    MessageResponse createAppointment(Appointment appointment);

    List<Appointment> getAppointments();

    Optional<Appointment> getAppointment(Long id);

     List<Appointment> getPatientAppointment(Long patientId);

    List<Appointment> getDoctorAppointment(Long doctorId);

    List<Appointment> getNurseAppointment(Long nurseId);

    MessageResponse updateAppointment(Long id,Appointment entity);

    MessageResponse deleteAppointment(Long id);

}
