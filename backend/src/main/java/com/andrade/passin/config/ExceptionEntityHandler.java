package com.andrade.passin.config;

import com.andrade.passin.domain.attendee.exceptions.AttendeeAlreadyRegisteredException;
import com.andrade.passin.domain.attendee.exceptions.AttendeeNotFoundException;
import com.andrade.passin.domain.checkIn.exceptions.AttendeeAlreadyCheckedException;
import com.andrade.passin.domain.event.excepions.EventFullException;
import com.andrade.passin.domain.event.excepions.EventNotFoundException;
import com.andrade.passin.dto.exceptions.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

  @ExceptionHandler(EventNotFoundException.class)
  public ResponseEntity<?> handleEventNotFoundException(EventNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(EventFullException.class)
  public ResponseEntity<ErrorResponseDTO> handleEventNotFoundException(EventFullException ex) {
    return ResponseEntity.badRequest().body(new ErrorResponseDTO(ex.getMessage()));
  }

  @ExceptionHandler(AttendeeAlreadyCheckedException.class)
  public ResponseEntity<?> handleEventNotFoundException(AttendeeAlreadyCheckedException ex) {
    return ResponseEntity.status(409).build();
  }

  @ExceptionHandler(AttendeeNotFoundException.class)
  public ResponseEntity<?> handleEventNotFoundException(AttendeeNotFoundException ex) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(AttendeeAlreadyRegisteredException.class)
  public ResponseEntity<?> handleEventNotFoundException(AttendeeAlreadyRegisteredException ex) {
    return ResponseEntity.status(409).build();
  }

}
