package com.example.demo.model;


import javax.persistence.*;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String doctorPrescription;

    @ManyToOne(cascade=CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name ="patientId"), name ="patientId")
    private Patient patient;

    @ManyToOne(cascade=CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name ="doctorId"), name ="doctorId")
    private Doctor doctor;

    @ManyToOne(cascade=CascadeType.DETACH,fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name ="nurseId"), name ="nurseId")
    private Nurse nurse ;

    public Appointment(){

    }

    public Appointment(Long id, String doctorPrescription, Patient patient, Doctor doctor, Nurse nurse) {
        this.id = id;
        this.doctorPrescription = doctorPrescription;
        this.patient = patient;
        this.doctor = doctor;
        this.nurse = nurse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorPrescription() {
        return doctorPrescription;
    }

    public void setDoctorPrescription(String doctorPrescription) {
        this.doctorPrescription = doctorPrescription;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }
}

