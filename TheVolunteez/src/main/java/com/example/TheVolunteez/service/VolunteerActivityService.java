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
import java.util.Optional;
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
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findSearchVolunteers(String searchKeyword, Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findByTitleContaining(searchKeyword, pageable);

        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findShortTermVolunteers(Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findShortTerm(pageable);

        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findShortTermBySearch(String searchKeyword, Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findShortTermBySearch(searchKeyword, pageable);

        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findLongTermVolunteers(Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findLongTerm(pageable);

        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public Page<PostVolunteerDto> findLongTermBySearch(String searchKeyword, Pageable pageable) {
        Page<VolunteerActivity> page = volunteerActivityRepository.findLongTermBySearch(searchKeyword, pageable);

        return page.map(v -> new PostVolunteerDto(v.getWriterId(), v.getTitle(), v.getDescription(), v.getDeadline(), v.getStartDate(), v.getEndDate(),
                v.getPlace(), v.getVolunteerHour(), v.getMaxPeople(), v.getCurrentPeople(), v.getContact()));
    }

    public PostVolunteerDto post(Authentication authentication, PostVolunteerDto postVolunteerDto) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String writerId = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        ).getUserId();

        VolunteerActivity volunteerActivity = volunteerActivityRepository.save(new VolunteerActivity(postVolunteerDto, writerId));
        postVolunteerDto.setWriterId(writerId);
        postVolunteerDto.setPeriod(volunteerActivity.getPeriod());
        return postVolunteerDto;
    }

    public String editVolunteerActivity(Authentication authentication, PostVolunteerDto postVolunteerDto, Long vid) throws IllegalAccessException {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("없는 게시판")
        );
        if (details.getUsername().equals(volunteerActivity.getWriterId())){
            volunteerActivity.editVolunteerActivity(postVolunteerDto);
            volunteerActivityRepository.save(volunteerActivity);
        }else{
            throw new IllegalAccessException("권한이 없습니다");
        };
        return "수정 성공";
    }

    public String deleteVolunteerActivity(Authentication authentication, Long vid) throws IllegalAccessException {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        String userId = details.getUsername();
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("없는 게시판")
        );
        if (userId.equals(volunteerActivity.getWriterId())) {
            volunteerActivityRepository.delete(volunteerActivity);
        }else{
            throw new IllegalAccessException("권한이 없습니다");
        }
        return "삭제 성공";
    }

    public String volunteerApply(Authentication authentication, Long vid) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판")
        );
        MemberVolunteer memberVolunteer = new MemberVolunteer(member, volunteerActivity);
        memberVolunteer.apply();
        volunteerActivity.plusCurrentPeople();
        memberVolunteerRepository.save(memberVolunteer);

        return "apply 성공!";
    }

    public String cancelApply(Authentication authentication, Long vid) {
        UserDetails details = (UserDetails) authentication.getPrincipal();
        Member member = memberRepository.findByUserId(details.getUsername()).orElseThrow(
                () -> new NullPointerException("로그인 먼저")
        );
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판")
        );
        MemberVolunteer memberVolunteer = memberVolunteerRepository.findMemberVolunteer(member.getUserId(), vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 신청정보")
        );
        volunteerActivity.minusCurrentPeople();
        memberVolunteerRepository.delete(memberVolunteer);

        return "apply 취소 성공";
    }

    public List<String> volunteerMemberNameList (Long vid) {
        VolunteerActivity volunteerActivity = volunteerActivityRepository.findById(vid).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시판")
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
        Optional<LikeVolunteer> likeVolunteer = likeVolunteerRepository.findLikeVolunteer(member.getLikeList().getId(), vid);

        if (likeVolunteer.isEmpty()) {
            LikeVolunteer newLikeVolunteer = new LikeVolunteer(member.getLikeList(), volunteerActivity);
            newLikeVolunteer.Like();
            volunteerActivity.plusLikeCount();
            likeVolunteerRepository.save(newLikeVolunteer);
            return "좋아요 성공";
        }else{
            volunteerActivity.minusLikeCount();
            likeVolunteerRepository.delete(likeVolunteer.get());
            return "좋아요 취소 성공";
        }
    }
}
