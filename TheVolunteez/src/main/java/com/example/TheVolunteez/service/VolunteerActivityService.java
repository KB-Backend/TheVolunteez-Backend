package com.example.TheVolunteez.service;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.*;
import com.example.TheVolunteez.repository.LikeVolunteerRepository;
import com.example.TheVolunteez.repository.MemberRepository;
import com.example.TheVolunteez.repository.MemberVolunteerRepository;
import com.example.TheVolunteez.repository.VolunteerActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteerActivityService {

    private final VolunteerActivityRepository volunteerActivityRepository;
    private final MemberRepository memberRepository;
    private final MemberVolunteerRepository memberVolunteerRepository;
    private final LikeVolunteerRepository likeVolunteerRepository;


    public Page<PostVolunteerDto> findAllVolunteers(Pageable pageable) {

        Page<VolunteerActivity> page = volunteerActivityRepository.findAll(pageable);
        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findSearchVolunteers(String searchKeyword, Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findByTitleContaining(searchKeyword, pageable);
        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getContact()));
    }

    public PostVolunteerDto post(Authentication authentication, PostVolunteerDto postVolunteerDto) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String writerId = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        ).getUserId();

        volunteerActivityRepository.save(new VolunteerActivity(postVolunteerDto, writerId));
        postVolunteerDto.setWriterId(writerId);
        return postVolunteerDto;
    }

    public PostVolunteerDto findVolunteer(Long vid) {
        return volunteerActivityRepository.findVolunteerDto(vid);
    }

    public String volunteerApply(Authentication authentication, Long volunteerActivityId) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
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

    public String likeVolunteer(Authentication authentication, Long vid) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
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
