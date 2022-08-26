package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.*;
import com.example.TheVolunteez.repository.LikeVolunteerRepository;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.repository.MemberVolunteerRepository;
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
public class VolunteerActivityService {

    private final VolunteerActivityRepository volunteerActivityRepository;
    private final MemberRepository memberRepository;
    private final MemberVolunteerRepository memberVolunteerRepository;
    private final LikeVolunteerRepository likeVolunteerRepository;

    public PostVolunteerDto post(HttpServletRequest request, PostVolunteerDto postVolunteerDto) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member");
        String writerId = memberRepository.findById(memberId).orElseThrow(
                () -> new NullPointerException("로그인 먼저 해주십쇼")
        ).getUserId();
        volunteerActivityRepository.save(new VolunteerActivity(postVolunteerDto, writerId));
        postVolunteerDto.setWriterId(writerId);
        return postVolunteerDto;
    }

    public PostVolunteerDto findVolunteer(Long vid) {
        return volunteerActivityRepository.findVolunteerDto(vid);
    }

    public String volunteerApply(HttpServletRequest request, Long volunteerActivityId) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member");
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NullPointerException("로그인 먼저 해주십쇼")
        );
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(volunteerActivityId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판인데요")
        );
        MemberVolunteer memberVolunteer = new MemberVolunteer(member, volunteerActivity);
        memberVolunteer.apply();
        memberVolunteerRepository.save(memberVolunteer);
        return "apply 성공!";
    }

    public List<String> volunteerMemberNameList (Long vid) {
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findByIdFetch(vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판인데요")
        );
        List<String> memberList = volunteerActivity.getMemberList().stream()
                .map(m -> m.getMember().getName())
                .collect(Collectors.toList());
        return memberList;
    }

    public String likeVolunteer(HttpServletRequest request, Long vid) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("member");
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판")
        );
        LikeVolunteer likeVolunteer = new LikeVolunteer(member.getLikeList(), volunteerActivity);
        likeVolunteer.Like();
        likeVolunteerRepository.save(likeVolunteer);
        return "좋아요 성공!";
    }
}
