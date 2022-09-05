package com.example.TheVolunteez.entity;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VolunteerActivity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "volunteer_activity_id")
    private Long id;
    private String writerId;
    private String title;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private long period;
    private int volunteerHour;
    private String place;
    private int maxPeople;
    private int currentPeople;
    private String contact;

    @Enumerated(value = EnumType.STRING)
    private VolunteerStatus volunteerStatus;

    @OneToMany(mappedBy = "volunteerActivity")
    private List<MemberVolunteer> memberList = new ArrayList<>();

    @OneToMany(mappedBy = "volunteerActivity")
    private List<LikeVolunteer> likeVolunteers = new ArrayList<>();

    public VolunteerActivity(PostVolunteerDto postVolunteerDto, String writerId) {
        this.writerId = writerId;
        this.title = postVolunteerDto.getTitle();
        this.description = postVolunteerDto.getDescription();
        this.deadline = postVolunteerDto.getDeadline();
        this.startDate = postVolunteerDto.getStartDate();
        this.endDate = postVolunteerDto.getEndDate();
        this.volunteerHour = postVolunteerDto.getVolunteerHour();
        this.place = postVolunteerDto.getPlace();
        this.maxPeople = postVolunteerDto.getMaxPeople();
        this.contact = postVolunteerDto.getContact();
        this.volunteerStatus = VolunteerStatus.PARTICIPATING;
        this.period = postVolunteerDto.getPeriod();
    }

    public void addMember(MemberVolunteer memberVolunteer) {
        this.memberList.add(memberVolunteer);
    }

    public void addLikeMember(LikeVolunteer likeVolunteer) {
        this.likeVolunteers.add(likeVolunteer);
    }

}
