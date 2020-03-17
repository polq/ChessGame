package org.buzevych.controller;

import org.buzevych.model.UserModel;
import org.buzevych.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class SecurityController {

  @Autowired SecurityService securityService;

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("user", new UserModel());
    return "login";
  }

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("user", new UserModel());
    model.addAttribute("loginError", true);
    return "login";
  }

  @GetMapping("/registration")
  public String registrationPage(Model model) {
    model.addAttribute("user", new UserModel());
    return "registration";
  }

  @PostMapping("/registration")
  public String register(@ModelAttribute("user") @Valid UserModel user, Model model) {
    try {
      securityService.registerNewUser(user);
    } catch (IllegalArgumentException e) {
      model.addAttribute("error", "user with the " + user.getUsername() + " username already exists");
      return "registration";
    }
    return "login";
  }

  /*@PostMapping("/login")
  public String login(@ModelAttribute("user") @Valid UserModel user){
    return "index";
  }*/

}
