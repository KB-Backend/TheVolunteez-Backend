package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.entity.VolunteerActivity;
import com.example.TheVolunteez.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup") // 회원가입
    public String signUp(@RequestBody SignUpDto signUpDto) {
        memberService.signUp(signUpDto);
        return "회원가입 성공!";
    }

    @GetMapping("/members") // 모든멤버 확인
    public List<String> members() {
        return memberService.showAllMembers();
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        memberService.login(loginDto, request);
        return "로그인 성공!";
    }

    @GetMapping("/member/current") // 현재멤버 확인
    public String getCurrentUser(HttpServletRequest request) {
        return memberService.getUser(request);
    }

    @GetMapping("/myLikeList") // 나의 좋아요 리스트
    public List<String> myLikeList(HttpServletRequest request) {
        return memberService.showLikeList(request);
    }
}
