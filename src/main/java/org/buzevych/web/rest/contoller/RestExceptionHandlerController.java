package org.buzevych.web.rest.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerController {
  /**
   * Method that is used to handle {@link IllegalArgumentException} exception while starting a new
   * game and returning "index" page with the error message.
   *
   * @param throwable represents exception that was caught
   * @return {@link String} that identifies which file will be loaded
   */
  @ExceptionHandler({RuntimeException.class})
  private ResponseEntity runtimeExceptionHandler(Throwable throwable) {
    return ResponseEntity.badRequest().body(throwable.getMessage());
  }
}
