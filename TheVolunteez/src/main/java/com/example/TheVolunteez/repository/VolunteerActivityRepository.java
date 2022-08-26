package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.Member;
import com.example.TheVolunteez.entity.VolunteerActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long> {
    @Query("select new com.example.TheVolunteez.dto.PostVolunteerDto(va.writerId, va.title," +
            "va.description, va.deadline, va.startDate, va.endDate, va.place," +
            "va.volunteerHour, va.maxPeople, va.contact)" +
            "from VolunteerActivity va where va.id = :va_id")
    PostVolunteerDto findVolunteerDto(@Param("va_id") Long volunteerId);

    @Query("select v from VolunteerActivity v join fetch v.memberList vm join fetch vm.member")
    Optional<VolunteerActivity> findByIdFetch(Long id);

}