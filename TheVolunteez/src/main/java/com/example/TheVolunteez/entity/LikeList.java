package com.example.TheVolunteez.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeList {

    @Id @GeneratedValue
    @Column(name = "like_list_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "likeList")
    private List<LikeVolunteer> likeVolunteers = new ArrayList<>();

    public LikeList(Member member) {
        this.member = member;
    }

    public void addLikeVolunteer(LikeVolunteer likeVolunteer) {
        this.likeVolunteers.add(likeVolunteer);
    }
}
