package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder encoder;

    public String login(LoginDto loginDto) {
        Member member = memberRepository.findByUserId(loginDto.getUserId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다")
        );

        if(!encoder.matches(loginDto.getPassword(), member.getPassword())){
            return "비밀번호가 일치하지 않습니다.";
        }else{
            return jwtTokenProvider.createToken(member.getUserId(), member.getRoles());
        }
    }

}
