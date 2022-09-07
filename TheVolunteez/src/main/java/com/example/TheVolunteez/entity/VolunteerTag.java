package com.example.TheVolunteez.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class VolunteerTag {

    @Id
    @GeneratedValue
    @Column(name = "volunteer_tag")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_activity_id")
    private VolunteerActivity volunteerActivity;

    private String tagName;
    private Long tagNum;

    public VolunteerTag(Tag tag, VolunteerActivity volunteerActivity) {
        this.tag = tag;
        this.tagName = tag.getName();
        this.tagNum = getTagNum();
        this.volunteerActivity = volunteerActivity;
        this.volunteerActivity.addVolunteerTag(this);

    }
}
