package com.example.TheVolunteez.dto;

import com.example.TheVolunteez.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {
    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String address;
    private String university;
    private Gender gender;

    public SignUpDto(String userId, String password, String name, String nickname, String phoneNumber, String email, String address, String university, Gender gender) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
        this.gender = gender;
    }
}

