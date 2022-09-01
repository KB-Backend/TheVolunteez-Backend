package com.example.TheVolunteez.controller;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.VolunteerActivity;
import com.example.TheVolunteez.repository.VolunteerActivityRepository;
import com.example.TheVolunteez.service.VolunteerActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class VolunteerActivityController {

    private final VolunteerActivityService volunteerActivityService;
    private final VolunteerActivityRepository volunteerActivityRepository;

    @PostConstruct // 게시글 검색 기능 or 페이징 기능 테스트용 데이터
    public void init() {
        for (int i = 0; i < 100; i++) {
            PostVolunteerDto postVolunteerDto = new PostVolunteerDto("acg6138" + i, "제목" + i, "설명" + i, new Date(), new Date(), new Date(), "장소" + i,
                    i, i, "연락처" + i);
            volunteerActivityRepository.save(new VolunteerActivity(postVolunteerDto, "writerId" + 1));
        }
    }

    @GetMapping("/board/list") // 봉사활동 전체 게시글
    public Page<PostVolunteerDto> getAllVolunteers(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                                   String search) {
        System.out.println("searchKeyword = " + search);
        if(search == null) { // 검색한게 없을 때
            return volunteerActivityService.findAllVolunteers(pageable);
        }else{ // 검색 키워드가 있을 때
            return volunteerActivityService.findSearchVolunteers(search, pageable);
        }
    }

    @PostMapping("/board/post") // 봉사활동 게시글 올리기
    public PostVolunteerDto postVolunteer(Authentication authentication, @RequestBody PostVolunteerDto postVolunteerDto) {
        return volunteerActivityService.post(authentication, postVolunteerDto);
    }

    @GetMapping("/board/{id}") // 봉사활동 게시글 페이지
    public PostVolunteerDto findVolunteer(@PathVariable("id") Long vid) {
        return volunteerActivityService.findVolunteer(vid);
    }

    @PostMapping("/board/{id}/apply") // 봉사활동 참여하기 버튼
    public String volunteerApply(Authentication authentication, @PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerApply(authentication, vid);
    }

    @GetMapping("/board/{id}/members") // 봉사활동 게시글에 참여한 멤버 확인
    public List<String> volunteerMemberList(@PathVariable("id") Long vid) {
        return volunteerActivityService.volunteerMemberNameList(vid);
    }

    @PostMapping("/board/{id}/like") // 좋아요 버튼
    public String likeVolunteer(@PathVariable("id") Long vid, Authentication authentication) {
        return volunteerActivityService.likeVolunteer(authentication, vid);
    }
}
