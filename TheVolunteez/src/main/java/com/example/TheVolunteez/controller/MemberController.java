package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.EditMemberDto;
import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.entity.*;
import com.example.TheVolunteez.repository.*;
import com.example.TheVolunteez.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final VolunteerActivityRepository volunteerActivityRepository;
    private final TagRepository tagRepository;
    private final MemberTagRepository memberTagRepository;
    private final MemberVolunteerRepository memberVolunteerRepository;

//    @PostConstruct
//    public void init() {
//        Member member = Member.builder()
//                .roles(Collections.singletonList("ROLE_USER"))
//                .university("단국대학교")
//                .phoneNumber("01084145255")
//                .password(passwordEncoder.encode("wjddn6138"))
//                .name("이정우")
//                .userId("acg6138")
//                .address("수지")
//                .email("acg6138@naver.com")
//                .gender(Gender.MALE)
//                .build();
//        member.resetLikeList(new LikeList(member));
//        memberRepository.save(member);
//    }

    @PostMapping("/signup") // 회원가입
    public String signUp(@Valid @RequestBody SignUpDto signUpDto) {
        memberService.signUp(signUpDto);
        return "회원가입 성공!";
    }

    @GetMapping("/member/myLikeList") // 나의 좋아요 리스트
    public List<String> myLikeList(Authentication authentication) {
        return memberService.showLikeList(authentication);
    }

    @GetMapping("/member/edit")
    public EditMemberDto getEditMemberDto(Authentication authentication) {
        return memberService.getEditMember(authentication);
    }

    @PatchMapping("/member/edit")
    public String editMember(Authentication authentication, @Valid @RequestBody EditMemberDto editMemberDto) {
        return memberService.editMember(authentication, editMemberDto);
    }

    @DeleteMapping("/member/delete")
    public String deleteMember(Authentication authentication) {
        return memberService.deleteMember(authentication);
    }
}
