package com.example.TheVolunteez.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberVolunteer {

    @Id @GeneratedValue
    @Column(name = "member_volunteer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_activity_id")
    private VolunteerActivity volunteerActivity;

    @Enumerated
    private VolunteerStatus volunteerStatus;

    public MemberVolunteer(Member member, VolunteerActivity volunteerActivity) {
        this.member = member;
        this.volunteerActivity = volunteerActivity;
    }

    public void apply() {
        volunteerActivity.addMember(this);
        member.addVolunteer(this);
    }
}
