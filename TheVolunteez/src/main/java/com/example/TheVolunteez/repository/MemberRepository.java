package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);
    @Query("select m from Member m join fetch m.likeList ml " +
            "join fetch ml.likeVolunteers mlv " +
            "join fetch mlv.volunteerActivity " +
            "where m.userId = :userId"
    )
    Optional<Member> findByUserIdFetch(@Param("userId") String userId);

}
