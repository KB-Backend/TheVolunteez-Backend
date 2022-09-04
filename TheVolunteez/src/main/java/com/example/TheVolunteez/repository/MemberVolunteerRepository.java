package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.entity.MemberVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberVolunteerRepository extends JpaRepository<MemberVolunteer, Long> {

    @Query("select mv from MemberVolunteer mv where mv.member.userId = :userId " +
            "and mv.volunteerActivity.id = :vid")
    Optional<MemberVolunteer> findMemberVolunteer(@Param("userId") String userId, @Param("vid") Long vid);
}
