package com.example.TheVolunteez.entity;

import com.example.TheVolunteez.dto.SignUpDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;

    private String password;

    private String name;

    private String phoneNumber;

    private String email;

    private String address;

    private String university;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberVolunteer> volunteerList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private LikeList likeList;

    public Member(SignUpDto signUpDto) {
        this.userId = signUpDto.getUserId();
        this.password = signUpDto.getPassword();
        this.name = signUpDto.getName();
        this.phoneNumber = signUpDto.getPhoneNumber();
        this.email = signUpDto.getEmail();
        this.address = signUpDto.getAddress();
        this.university = signUpDto.getUniversity();
        this.likeList = new LikeList(this);
    }

    public void addVolunteer(MemberVolunteer memberVolunteer) {
        this.volunteerList.add(memberVolunteer);
    }
}
