package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.dto.PostVolunteerDto;
import com.example.TheVolunteez.entity.VolunteerActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolunteerActivityRepository extends JpaRepository<VolunteerActivity, Long> {
    @Query("select new com.example.TheVolunteez.dto.PostVolunteerDto(va.writerId, va.title," +
            "va.description, va.deadline, va.startDate, va.endDate, va.place," +
            "va.volunteerHour, va.maxPeople, va.contact)" +
            "from VolunteerActivity va where va.id = :va_id")
    PostVolunteerDto findVolunteerDto(@Param("va_id") Long volunteerId);

    Page<VolunteerActivity> findByTitleContaining(String searchKeyword, Pageable pageable);

}