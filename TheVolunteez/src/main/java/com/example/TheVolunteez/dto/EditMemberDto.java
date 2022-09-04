package com.example.TheVolunteez.dto;

import lombok.Data;

@Data
public class EditMemberDto {
    private String nickname;
    private String phoneNumber;
    private String email;
    private String address;
    private String university;

    public EditMemberDto(String nickname, String phoneNumber, String email, String address, String university) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
    }
}
