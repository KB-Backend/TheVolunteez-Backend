package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.VolunteerActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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


}