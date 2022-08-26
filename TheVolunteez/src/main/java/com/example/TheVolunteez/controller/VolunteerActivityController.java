package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.VolunteerActivity;
import com.example.TheVolunteez.service.VolunteerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolunteerActivityController {

    private final VolunteerActivityService volunteerActivityService;

    @PostMapping("/post") // 봉사활동 게시글 올리기
    public PostVolunteerDto postVolunteer(HttpServletRequest request, @RequestBody PostVolunteerDto postVolunteerDto) {
        return volunteerActivityService.post(request, postVolunteerDto);
    }

    @GetMapping("/post/{id}") // 봉사활동 게시글 찾기
    public PostVolunteerDto findVolunteer(@PathVariable("id") Long vid) {
        return volunteerActivityService.findVolunteer(vid);
    }

    @PostMapping("/post/{id}/apply") // 봉사활동 참여하기 버튼
    public String volunteerApply(HttpServletRequest request, @PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerApply(request, vid);
    }

    @GetMapping("/post/{id}/members") // 봉사활동 게시글에 참여한 멤버 확인
    public List<String> volunteerMemberList(@PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerMemberNameList(vid);
    }

    @PostMapping("/post/{id}/like") // 좋아요 버튼
    public String likeVolunteer(@PathVariable("id") Long vid, HttpServletRequest request) {
        return volunteerActivityService.likeVolunteer(request, vid);
    }
}
