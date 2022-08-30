package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginDto loginDto) {
        Member member = memberRepository.findByUserId(loginDto.getUserId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다")
        );
        return jwtTokenProvider.createToken(member.getUserId(), member.getRoles());
    }

}
