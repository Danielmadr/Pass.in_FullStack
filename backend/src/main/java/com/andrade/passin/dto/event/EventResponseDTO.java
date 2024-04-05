package com.andrade.passin.dto.event;

import com.andrade.passin.domain.event.Event;
import lombok.Getter;

@Getter
public class EventResponseDTO {
  EventDescriptionDTO event;

  public EventResponseDTO( Event event, Integer numberOfAttendees) {
    this.event = new EventDescriptionDTO(
      event.getId(),
      event.getTitle(),
      event.getDescription(),
      event.getSlug(),
      event.getMaximumAttendees(),
      numberOfAttendees
    );

  }
}
