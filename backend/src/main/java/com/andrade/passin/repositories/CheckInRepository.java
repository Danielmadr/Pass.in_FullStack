package com.andrade.passin.repositories;

import com.andrade.passin.domain.checkIn.CheckIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Integer> {
  Optional<CheckIn> findByAttendeeId(String id);
}
