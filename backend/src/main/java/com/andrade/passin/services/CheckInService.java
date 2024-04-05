package com.andrade.passin.services;

import com.andrade.passin.domain.attendee.Attendee;
import com.andrade.passin.domain.checkIn.CheckIn;
import com.andrade.passin.domain.checkIn.exceptions.AttendeeAlreadyCheckedException;
import com.andrade.passin.repositories.CheckInRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CheckInService {
  private final CheckInRepository checkInRepository;

  public void registerCheckIn(Attendee attendee) {
    this.verifyCheckInExists(attendee.getId());
    CheckIn checkIn = new CheckIn();
    checkIn.setAttendee(attendee);
    checkIn.setCreatedAt(LocalDateTime.now());
    this.checkInRepository.save(checkIn);
  }

  private void verifyCheckInExists(String attendeeID) {
    Optional<CheckIn> checkIn = getCheckIn(attendeeID);
    if (checkIn.isPresent()) {
      throw new AttendeeAlreadyCheckedException("Attendee already checked-in");
    }
  }

  public Optional<CheckIn> getCheckIn(String id) {
    return this.checkInRepository.findByAttendeeId(id);
  }
}
