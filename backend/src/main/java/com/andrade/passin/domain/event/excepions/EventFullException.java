package com.andrade.passin.domain.event.excepions;

public class EventFullException extends RuntimeException {
  public EventFullException(String message) {
    super(message);
  }
}
