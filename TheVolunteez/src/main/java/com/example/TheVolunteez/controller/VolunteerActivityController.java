package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.service.VolunteerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolunteerActivityController {

    private final VolunteerActivityService volunteerActivityService;

    @PostMapping("/post") // 봉사활동 게시글 올리기
    public PostVolunteerDto postVolunteer(Authentication authentication, @RequestBody PostVolunteerDto postVolunteerDto) {
        return volunteerActivityService.post(authentication, postVolunteerDto);
    }

    @GetMapping("/post/{id}") // 봉사활동 게시글 찾기
    public PostVolunteerDto findVolunteer(@PathVariable("id") Long vid) {
        return volunteerActivityService.findVolunteer(vid);
    }

    @PostMapping("/post/{id}/apply") // 봉사활동 참여하기 버튼
    public String volunteerApply(Authentication authentication, @PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerApply(authentication, vid);
    }

    @GetMapping("/post/{id}/members") // 봉사활동 게시글에 참여한 멤버 확인
    public List<String> volunteerMemberList(@PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerMemberNameList(vid);
    }

    @PostMapping("/post/{id}/like") // 좋아요 버튼
    public String likeVolunteer(@PathVariable("id") Long vid, Authentication authentication) {
        return volunteerActivityService.likeVolunteer(authentication, vid);
    }
}
