package com.example.TheVolunteez.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String university;

    public SignUpDto(String userId, String password, String name, String phoneNumber, String email, String address, String university) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
    }
}

