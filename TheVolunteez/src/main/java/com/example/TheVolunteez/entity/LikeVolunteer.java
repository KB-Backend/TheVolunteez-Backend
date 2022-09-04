package com.example.TheVolunteez.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LikeVolunteer {

    @Id @GeneratedValue
    @Column(name = "like_volunteer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "like_list_id")
    private LikeList likeList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_activity_id")
    private VolunteerActivity volunteerActivity;

    public LikeVolunteer(LikeList likeList, VolunteerActivity volunteerActivity) {
        this.likeList = likeList;
        this.volunteerActivity = volunteerActivity;
    }

    public void Like() {
        this.likeList.addLikeVolunteer(this);
        this.volunteerActivity.addLikeMember(this);
    }

}
