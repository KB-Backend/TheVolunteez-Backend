package com.example.TheVolunteez.repository;

import com.example.TheVolunteez.entity.MemberVolunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberVolunteerRepository extends JpaRepository<MemberVolunteer, Long> {
}
