package com.example.TheVolunteez.dto;

import com.example.TheVolunteez.entity.Gender;
import com.example.TheVolunteez.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SignUpDto {

    @NotBlank(message = "아이디는 필수 값입니다.")
    private String userId;

    @NotBlank(message = "비밀번호는 필수 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()=|=]{8,16}",
            message = "비밀번호는 영문, 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자~20자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 값입니다.")
    private String nickname;

    @NotBlank(message = "전화번호는 필수 값입니다.")
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}", message = "000-0000-0000 형식으로 적어주세요")
    private String phoneNumber;

    @NotBlank(message = "이메일은 필수 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "주소는 필수 값입니다.")
    private String address;

    @NotBlank(message = "대학 정보는 필수 값입니다.")
    private String university;

    private Gender gender;
    private List<String> tags;

    public SignUpDto(String userId, String password, String name, String nickname, String phoneNumber, String email, String address, String university, Gender gender, List<String> tags) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
        this.gender = gender;
        this.tags = tags;
    }
}

