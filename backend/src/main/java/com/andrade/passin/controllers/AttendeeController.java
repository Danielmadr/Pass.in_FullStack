package com.andrade.passin.controllers;

import com.andrade.passin.dto.attendee.AttendeeBadgeResponseDTO;
import com.andrade.passin.services.AttendeeService;
import com.andrade.passin.services.CheckInService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/attendees")
@AllArgsConstructor
public class AttendeeController {
  private final AttendeeService attendeeService;
  @GetMapping("/{attendeeId}/badge")
  public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(@PathVariable String attendeeId, UriComponentsBuilder uriBuilder) {
    AttendeeBadgeResponseDTO attendeeBadge = attendeeService.getAttendeeBadge(attendeeId, uriBuilder);
    return ResponseEntity.ok(attendeeBadge);
  }

  @PostMapping("/{attendeeId}/check-in")
  public ResponseEntity<?> registerCheckIn(@PathVariable String attendeeId, UriComponentsBuilder uriBuilder) {
    this.attendeeService.CheckInAttendee(attendeeId);
    var uri = uriBuilder
            .path("/attendees/{attendeeId}/badge")
            .buildAndExpand(attendeeId).toUri();

    return ResponseEntity.created(uri).build();
  }
}
