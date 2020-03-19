package org.buzevych.controller;

import org.buzevych.model.UserModel;
import org.buzevych.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * {@link Controller} class that is supposed to handle request to requests to login and registration
 * pages.
 */
@Controller
public class SecurityController {

  SecurityService securityService;

  @Autowired
  public SecurityController(SecurityService securityService) {
    this.securityService = securityService;
  }

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("user", new UserModel());
    return "login";
  }

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return login(model);
  }

  @GetMapping("/registration")
  public String registrationPage(Model model) {
    model.addAttribute("user", new UserModel());
    return "registration";
  }

  @PostMapping("/registration")
  public String register(@ModelAttribute("user") UserModel user, Model model) {
    try {
      securityService.registerNewUser(user);
    } catch (IllegalArgumentException e) {
      model.addAttribute(
          "error", "user with the " + user.getUsername() + " username already exists");
      return "registration";
    }
    return "login";
  }
}
