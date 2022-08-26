package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.LoginDto;
import com.example.TheVolunteez.dto.SignUpDto;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.entity.VolunteerActivity;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.repository.VolunteerActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final VolunteerActivityRepository volunteerActivityRepository;

    public void signUp(SignUpDto signUpDto) {
        Member member = new Member(signUpDto);
        memberRepository.save(member);
    }

    public Long login(LoginDto loginDto, HttpServletRequest request) {
        Member member = memberRepository.findByUserId(loginDto.getUserId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 회원입니다")
        );
        if (!member.getPassword().equals(loginDto.getPassword())) {
            throw new NullPointerException("비밀번호가 일치하지 않습니다");
        }
        HttpSession session = request.getSession();
        session.setAttribute("member", member.getId());
        return member.getId();
    }

    public List<String> showAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<String> membersName = members.stream()
                .map(m -> m.getName())
                .collect(Collectors.toList());
        return membersName;
    }

    public String getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member");
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        return member.getName();
    }

    public List<String> showLikeList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member");
        Member member = memberRepository.findByIdFetch(memberId).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        List<String> result = member.getLikeList().getLikeVolunteers()
                .stream()
                .map(l -> l.getVolunteerActivity().getTitle())
                .collect(Collectors.toList());
        return result;
    }
}
