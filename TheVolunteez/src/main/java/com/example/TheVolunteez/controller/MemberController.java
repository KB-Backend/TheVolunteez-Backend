package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

    @GetMapping("/myLikeList") // 나의 좋아요 리스트
    public List<String> myLikeList(Authentication authentication) {
        return memberService.showLikeList(authentication);
    }
}
