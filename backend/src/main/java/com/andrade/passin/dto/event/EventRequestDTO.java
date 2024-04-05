package com.andrade.passin.dto.event;

public record EventRequestDTO(
        String title,
        String description,
        Integer maximumAttendees
) {
}
