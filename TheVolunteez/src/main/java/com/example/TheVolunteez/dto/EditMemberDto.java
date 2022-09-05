package com.example.TheVolunteez.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class EditMemberDto {

    @NotBlank(message="닉네임은 필수 입력 값입니다.")
    private String nickname;

    @NotBlank(message="전화번호는 필수 입력 값입니다.")
    private String phoneNumber;

    @NotBlank(message="이메일은 필수 입력 값입니다.")
    @Email(message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message="주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message="대학교는 필수 입력 값입니다.")
    private String university;

    public EditMemberDto(String nickname, String phoneNumber, String email, String address, String university) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
    }
}
