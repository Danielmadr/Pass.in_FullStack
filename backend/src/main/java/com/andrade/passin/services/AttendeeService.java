package com.andrade.passin.services;

import com.andrade.passin.domain.attendee.Attendee;
import com.andrade.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import com.andrade.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.andrade.passin.domain.checkIn.CheckIn;
import com.andrade.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.andrade.passin.dto.attendee.AttendeeDetails;
import com.andrade.passin.dto.attendee.AttendeesListResponseDTO;
import com.andrade.passin.dto.attendee.AttendeeBadgeDTO;
import com.andrade.passin.repositories.AttendeeRepository;
import com.andrade.passin.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
  private final AttendeeRepository attendeeRepository;
  private final CheckInService checkInService;

  public List<Attendee> getAllAttendeesForEvent(String eventId) {
    return this.attendeeRepository.findByEventId(eventId);
  }

  public AttendeesListResponseDTO getEventAttendees(String eventId) {
    List<Attendee> attendees = this.getAllAttendeesForEvent(eventId);
    List<AttendeeDetails> attendeeDetails = attendees
            .stream()
            .map(attendee -> {
              Optional<CheckIn> checkIn = this.checkInService.getCheckIn(attendee.getId());
              LocalDateTime checkInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);
              return new AttendeeDetails(
                      attendee.getId(),
                      attendee.getName(),
                      attendee.getEmail(),
                      attendee.getCreatedAt(),
                      checkInAt);
            }).toList();
    return new AttendeesListResponseDTO(attendeeDetails);
  }

  public void verifyAttendeeSubscription(String attendeeEmail, String eventId) {
    Optional<Attendee> attendee = this.attendeeRepository.findByEventIdAndEmail(eventId, attendeeEmail);
    if (attendee.isPresent()) {
      throw new AttendeeAlreadyRegisteredException("Attendee already registered on this event");
    }
  }

  public void registerAttendee(Attendee attendee) {
    this.attendeeRepository.save(attendee);
  }

  public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriBuilder) {
    Attendee attendee = getAttendee(attendeeId);
    var uri = uriBuilder
            .path("/attendees/{attendeeId}/check-in")
            .buildAndExpand(attendeeId).toUri().toString();

    AttendeeBadgeDTO badge = new AttendeeBadgeDTO(
            attendee.getName(),
            attendee.getEmail(),
            uri,
            attendee.getEvent().getId());

    return new AttendeeBadgeResponseDTO(badge);
  }

  public void CheckInAttendee(String attendeeId) {
    Attendee attendee = getAttendee(attendeeId);
    this.checkInService.registerCheckIn(attendee);
  }
  private Attendee getAttendee(String attendeeId) {
    return this.attendeeRepository.findById(attendeeId).orElseThrow(
            () -> new AttendeeNotFoundException("Attendee not found with id " + attendeeId)
    );
  }
}
