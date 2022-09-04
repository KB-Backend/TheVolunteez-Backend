package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.entity.LikeVolunteer;
import com.example.TheVolunteez.entity.MemberVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeVolunteerRepository extends JpaRepository<LikeVolunteer, Long> {
    @Query("select lv from LikeVolunteer lv where lv.likeList.id = :listId " +
            "and lv.volunteerActivity.id = :vid")
    Optional<LikeVolunteer> findLikeVolunteer(@Param("listId") Long listId, @Param("vid") Long vid);
}
