package com.example.demo.payload.request;

import com.example.demo.model.Appointment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NurseRequest {
    @Column(nullable = false)
    @NotBlank(message = "This field is required")
    @NotNull
    @Size(min = 2, message = "First Name should have at least 2 characters")
    private String firstName;

    @Column
    @NotBlank(message = "This field is required")
    @NotNull
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "This field is required")
    @NotNull
    private String email;

    @Column
    @NotBlank(message = "This field is required")
    @NotNull
    private String address;

    @Column
    @NotBlank(message = "This field is required")
    @NotNull
    private String dateOfBirth;

    @Column
    @NotBlank(message = "This field is required")
    @NotNull
    private String specialty;

    private String phoneNumber;

    @Column
    private String username;

    @Column
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "nurse")
    private Appointment appointments;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Appointment getAppointments() {
        return appointments;
    }

    public void setAppointments(Appointment appointments) {
        this.appointments = appointments;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String department) {
        this.specialty = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
