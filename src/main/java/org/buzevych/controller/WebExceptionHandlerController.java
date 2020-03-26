package org.buzevych.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

/**
 * Controller Advise annotated class that is used to create Spring MVC exception handlers. Has
 * method that are used to handle exceptional situations occurred while executing a program
 */
@ControllerAdvice
public class WebExceptionHandlerController {

  /**
   * Method that is used to handle {@link IllegalArgumentException} exception while starting a new
   * game and returning "index" page with the error message.
   *
   * @param throwable represents exception that was caught
   * @param model used to access HTML based file and insert appropriate
   * @return {@link String} that identifies which file will be loaded
   */
  @ExceptionHandler({IllegalArgumentException.class})
  private String illegalArgumentHandler(Throwable throwable, Model model) {
    model.addAttribute("error", throwable.getMessage());
    return "index";
  }

  /**
   * Method that is used to handle {@link IOException} exception while reading load file.
   *
   * @param throwable represents exception that was caught
   * @param model used to access HTML based file and insert appropriate
   * @return {@link String} that identifies which file will be loaded
   */
  @ExceptionHandler({IOException.class})
  private String ioExceptionHandler(Throwable throwable, Model model) {
    model.addAttribute(
        "error", "There was some exception while reading a file " + throwable.getMessage());
    return "index";
  }

  /**
   * Method that is used to handle the unappropriated request to make a move without starting a
   * game.
   *
   * @param throwable represents exception that was caught
   * @param model used to access HTML based file and insert appropriate
   * @return {@link String} that identifies which file will be loaded
   */
  @ExceptionHandler(NullPointerException.class)
  private String nullPointerHandler(Throwable throwable, Model model) {
    model.addAttribute(
        "error", "There was a error while trying to make a move without starting a game");
    return "index";
  }
}
