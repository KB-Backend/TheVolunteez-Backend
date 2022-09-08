package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.entity.VolunteerActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long> {
    Page<VolunteerActivity> findByTitleContaining(String searchKeyword, Pageable pageable);

    @Query("select v from VolunteerActivity v where v.place like %:searchKeyword")
    Page<VolunteerActivity> findZone(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select v from VolunteerActivity v where v.title like %:searchKeyword and v.period <= 3")
    Page<VolunteerActivity> findShortTermBySearch(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select v from VolunteerActivity v where v.period <= 3")
    Page<VolunteerActivity> findShortTerm(Pageable pageable);

    @Query("select v from VolunteerActivity v where v.title like %:searchKeyword and v.period > 3")
    Page<VolunteerActivity> findLongTermBySearch(@Param("searchKeyword") String searchKeyword, Pageable pageable);

    @Query("select v from VolunteerActivity v where v.period > 3")
    Page<VolunteerActivity> findLongTerm(Pageable pageable);

    @Query(value = "SELECT DISTINCT(VT.VOLUNTEER_ACTIVITY_ID) FROM VOLUNTEER_ACTIVITY AS V JOIN VOLUNTEER_TAG AS VT ON V.VOLUNTEER_ACTIVITY_ID = VT.VOLUNTEER_ACTIVITY_ID " +
            "WHERE VT.TAG_NAME IN (SELECT MT.TAG_NAME FROM MEMBER M JOIN MEMBER_TAG MT ON M.MEMBER_ID = MT.MEMBER_ID WHERE M.MEMBER_ID = :memberId)", nativeQuery = true)
    List<Long> findMyPick(@Param("memberId") Long memberId);

    @Query(value = "select * from VOLUNTEER_ACTIVITY V where V.VOLUNTEER_ACTIVITY_ID in (:idList)", nativeQuery = true)
    Page<VolunteerActivity> findMyPickVolunteer(@Param("idList") List<Long> idList, Pageable pageable);

    @Query(value = "select * from VOLUNTEER_ACTIVITY V where V.VOLUNTEER_ACTIVITY_ID in (:idList) AND V.TITLE LIKE %:searchKeyword", nativeQuery = true)
    Page<VolunteerActivity> findMyPickVolunteerBySearch(@Param("idList") List<Long> idList, @Param("searchKeyword") String searchKeyword, Pageable pageable);


}