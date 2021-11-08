package com.example.demo.security.service;

import com.example.demo.model.Appointment;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.AppointmentRepo;
import com.example.demo.repository.DoctorRepo;
import com.example.demo.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private PatientRepo patientRepo;
    @Autowired
    private DoctorRepo doctorRepo;

    //Add Appointments
    @Transactional
    @Override
   public MessageResponse createAppointment(Appointment appointment) {
      Doctor doctor = doctorRepo.findById(appointment.getDoctor().getId()).orElse(null);
      Patient patient = patientRepo.findById(appointment.getPatient().getId()).orElse(null);

      if(null == doctor){
          return new MessageResponse("Doctor doesn't exist");
      }
        appointment.setDoctor(doctor);
        if(null == patient){
            return new MessageResponse("Patient doesn't exist");
           }
        appointment.setPatient(patient);
        appointment.setId(appointment.getId());
       appointmentRepo.save(appointment);
       return new MessageResponse("New Appointment created");
    }

    //Get all Appointments
    @Transactional
    @Override
    public List<Appointment> getAppointments() {
        return appointmentRepo.findAll();
    }

    //Get one appointment by id
    @Transactional
    @Override
    public Optional<Appointment> getAppointment(@PathVariable Long id) {
        return appointmentRepo.findById(id);
    }

//    //Get all appointments for a patient
@Transactional
@Override
public List<Appointment> getPatientAppointment(Long id){
        return appointmentRepo.getPatientAppointments(id);
}

    //    //Get all appointments for a patient
    @Transactional
    @Override
    public List<Appointment> getDoctorAppointment(Long id){
        return appointmentRepo.getDoctorAppointments(id);
    }

    //    //Get all nurses for a patient
    @Transactional
    @Override
    public List<Appointment> getNurseAppointment(Long id){
        return appointmentRepo.getNurseAppointments(id);
    }


    //Update an appointment
    @Transactional
    @Override
    public MessageResponse updateAppointment(Long id,Appointment appointment) {
        Optional<Appointment> appointmentFromDatabase = appointmentRepo.findById(id);
        if (appointmentFromDatabase.isEmpty()){
            return new MessageResponse("Appointment doesn't exist");
        }
        appointmentFromDatabase.get().setDoctorPrescription(appointment.getDoctorPrescription());
        appointmentFromDatabase.get().setDoctor(appointment.getDoctor());
        appointmentFromDatabase.get().setNurse(appointment.getNurse());
        appointmentFromDatabase.get().setPatient(appointment.getPatient());
       appointmentRepo.save(appointmentFromDatabase.get());
        return new MessageResponse("Appointment details updated");
    }

    //Delete an appointment
    @Transactional
    @Override
    public MessageResponse deleteAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentRepo.findById(id).get();
        appointmentRepo.deleteById(id);
        return new MessageResponse("Appointment Deleted");
    }
}
