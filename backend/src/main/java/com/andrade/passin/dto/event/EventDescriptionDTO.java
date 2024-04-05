package com.andrade.passin.dto.event;

import jakarta.persistence.criteria.CriteriaBuilder;

public record EventDescriptionDTO(
  String id,
  String title,
  String description,
  String slug,
  Integer maxAttendees,
  Integer attendeesAmount
) {
}
