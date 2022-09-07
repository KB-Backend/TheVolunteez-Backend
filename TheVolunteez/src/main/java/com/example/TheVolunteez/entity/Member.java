package com.example.TheVolunteez.entity;

import com.example.TheVolunteez.dto.EditMemberDto;
import com.example.TheVolunteez.dto.SignUpDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String name;
    private String nickname;
    private String phoneNumber;
    private String email;
    private String address;
    private String university;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberVolunteer> volunteerList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private LikeList likeList;

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberTag> memberTags = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Builder
    public Member(String userId, String password, String name, String nickname, String phoneNumber, String email, String address, String university, List<String> roles, Gender gender, List<MemberTag> tags) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.university = university;
        this.roles = roles;
        this.gender = gender;
        this.memberTags = tags;
    }

    public void resetLikeList(LikeList likeList) {
        this.likeList = likeList;
    }

    public void addVolunteer(MemberVolunteer memberVolunteer) {
        this.volunteerList.add(memberVolunteer);
    }

    public void addMemberTag(MemberTag memberTag) {
        this.memberTags.add(memberTag);
    }

    public void editMember(EditMemberDto editMemberDto) {
        this.nickname = editMemberDto.getNickname();
        this.phoneNumber = editMemberDto.getPhoneNumber();
        this.email = editMemberDto.getEmail();
        this.address = editMemberDto.getAddress();
        this.university = editMemberDto.getUniversity();
    }

}
