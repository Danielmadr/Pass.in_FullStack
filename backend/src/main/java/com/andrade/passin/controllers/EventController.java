package com.andrade.passin.controllers;

import com.andrade.passin.dto.attendee.AttendeeIdDTO;
import com.andrade.passin.dto.attendee.AttendeeRequestDTO;
import com.andrade.passin.dto.attendee.AttendeesListResponseDTO;
import com.andrade.passin.dto.event.EventIdDTO;
import com.andrade.passin.dto.event.EventRequestDTO;
import com.andrade.passin.dto.event.EventResponseDTO;
import com.andrade.passin.repositories.EventRepository;
import com.andrade.passin.services.AttendeeService;
import com.andrade.passin.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
  
  private final EventService eventService;
  private final AttendeeService attendeeService;

  @GetMapping("/{id}")
  public ResponseEntity<EventResponseDTO> getEvent(@PathVariable String id) {
    EventResponseDTO event = eventService.getEventDescription(id);
    return ResponseEntity.ok(event);
  }

  @PostMapping
  public ResponseEntity<EventIdDTO> createEvent(
          @RequestBody EventRequestDTO eventBody,
          UriComponentsBuilder uriComponentsBuilder) {

    EventIdDTO eventIdDTO = this.eventService.createEvent(eventBody);
    var uri = uriComponentsBuilder.path("/events/{id}").buildAndExpand(eventIdDTO.id()).toUri();
    return ResponseEntity.created(uri).body(eventIdDTO);
  }

  @GetMapping("/attendees/{id}")
  public ResponseEntity<AttendeesListResponseDTO> getEventAttendees(@PathVariable String id) {
    AttendeesListResponseDTO eventAttendees = this.attendeeService.getEventAttendees(id);
    return ResponseEntity.ok(eventAttendees);
  }

  @PostMapping("/{eventId}/attendees")
  public ResponseEntity<AttendeeIdDTO> registerAttendee(
          @PathVariable String eventId,
          @RequestBody AttendeeRequestDTO attendeeBody,
          UriComponentsBuilder uriComponentsBuilder) {

    AttendeeIdDTO attendeeIdDTO = this.eventService.registerAttendeeOnEvent(eventId, attendeeBody);

    var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge").buildAndExpand(attendeeIdDTO.attendeeId()).toUri();

    return ResponseEntity.created(uri).body(attendeeIdDTO);
  }
}
