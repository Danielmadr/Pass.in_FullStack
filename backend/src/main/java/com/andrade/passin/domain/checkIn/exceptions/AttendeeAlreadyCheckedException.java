package com.andrade.passin.domain.checkIn.exceptions;

public class AttendeeAlreadyCheckedException extends RuntimeException {
  public AttendeeAlreadyCheckedException(String message) {
    super(message);
  }
}
