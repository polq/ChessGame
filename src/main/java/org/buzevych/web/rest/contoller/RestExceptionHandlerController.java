package org.buzevych.web.rest.contoller;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandlerController {
  /**
   * Method that is used to handle {@link IllegalArgumentException} and {@link
   * UsernameNotFoundException}
   *
   * @param throwable represents exception that was caught
   * @return ResponseEntity wit
   */
  @ExceptionHandler({IllegalArgumentException.class, UsernameNotFoundException.class})
  private ResponseEntity runtimeExceptionHandler(Throwable throwable) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("error", throwable.getMessage());
    return ResponseEntity.badRequest().body(jsonObject.toString());
  }
}
