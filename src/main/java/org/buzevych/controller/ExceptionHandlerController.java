package org.buzevych.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler({IllegalArgumentException.class})
  public String illegalArgumentHandler(Throwable throwable, Model model){
    model.addAttribute("error", throwable.getMessage());
    return "index";
  }

  @ExceptionHandler({IllegalArgumentException.class})
  public String iOExceptionHandler(Throwable throwable, Model model){
    model.addAttribute("error", throwable.getMessage());
    return "index";
  }

}
