package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.entity.LikeList;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.repository.VolunteerActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final VolunteerActivityRepository volunteerActivityRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpDto signUpDto) {
        Member member = Member.builder()
                        .roles(Collections.singletonList("ROLE_USER"))
                        .university(signUpDto.getUniversity())
                        .address(signUpDto.getAddress())
                        .email(signUpDto.getEmail())
                        .name(signUpDto.getName())
                        .phoneNumber(signUpDto.getPhoneNumber())
                        .userId(signUpDto.getUserId())
                        .password(passwordEncoder.encode(signUpDto.getPassword()))
                        .build();
        member.initLikeList(new LikeList(member));
        memberRepository.save(member);
    }

    public List<String> showLikeList(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserIdFetch(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        List<String> result = member.getLikeList().getLikeVolunteers()
                .stream()
                .map(l -> l.getVolunteerActivity().getTitle())
                .collect(Collectors.toList());
        return result;
    }
}
