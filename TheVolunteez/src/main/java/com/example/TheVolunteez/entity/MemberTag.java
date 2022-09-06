package com.example.TheVolunteez.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class MemberTag {

    @Id @GeneratedValue
    @Column(name = "member_tag_id")
    private Long id;

    private String tagName;
    private Long tagNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberTag(Tag tag, Member member) {
        this.tag = tag;
        this.tagNum = tag.getId();
        this.member = member;
        this.tagName = tag.getName();
        this.member.addMemberTag(this);
    }


}
