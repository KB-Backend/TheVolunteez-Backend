package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.EditMemberDto;
import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.entity.LikeList;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
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
        member.resetLikeList(new LikeList(member));
        memberRepository.save(member);
    }

    public List<String> showLikeList(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Optional<Member> likeListEmptyCheckMember = memberRepository.findByUserId(details.getUsername());
        if (likeListEmptyCheckMember.get().getLikeList().getLikeVolunteers().isEmpty()) {
            List<String> empty = new ArrayList<>();
            return empty;
        }
        Member member = memberRepository.findByUserIdFetch(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        List<String> result = member.getLikeList().getLikeVolunteers()
                .stream()
                .map(l -> l.getVolunteerActivity().getTitle())
                .collect(Collectors.toList());
        return result;
    }

    public String getMember(Authentication authentication) {
        UserDetails detail = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(detail.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        return member.getUserId();
    }

    public EditMemberDto getEditMember(Authentication authentication) {
        UserDetails detail = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(detail.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        return new EditMemberDto(member.getNickname(), member.getPhoneNumber(), member.getEmail(), member.getAddress(), member.getUniversity());

    }
    public String editMember(Authentication authentication, EditMemberDto editMemberDto) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 유저")
        );
        member.editMember(editMemberDto);
        memberRepository.save(member);
        return "회원 수정 완료";
    }

    public String deleteMember(Authentication authentication) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 유저")
        );
        memberRepository.delete(member);
        return "회원 삭제 성공";
    }
}
