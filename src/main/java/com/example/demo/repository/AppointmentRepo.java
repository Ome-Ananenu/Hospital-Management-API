package com.example.demo.repository;

import com.example.demo.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    @Query(value = "SELECT * FROM `hospital-crud`.appointment WHERE patient_id =:patient_id", nativeQuery = true)
    List<Appointment> getPatientAppointments(@Param("patient_id") Long patient_id);

    @Query(value = "SELECT * FROM `hospital-crud`.appointment WHERE doctor_id =:doctor_id", nativeQuery = true)
    List<Appointment> getDoctorAppointments(@Param("doctor_id") Long doctor_id);

    @Query(value = "SELECT * FROM `hospital-crud`.appointment WHERE nurse_id =:nurse_id", nativeQuery = true)
    List<Appointment> getNurseAppointments(@Param("nurse_id") Long nurse_id);
}
