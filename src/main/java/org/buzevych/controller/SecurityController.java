package org.buzevych.controller;

import org.buzevych.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SecurityController {

  @GetMapping("/login")
  public String login(Model model) {
    model.addAttribute("user", new UserDTO());
    return "login";
  }

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("user", new UserDTO());
    model.addAttribute("loginError", true);
    return "login";
  }

  @GetMapping("/registration")
  public String registrationPage(Model model){
    model.addAttribute("user", new UserDTO());
    return "registration";
  }

  @PostMapping("/login")
  public String login(@ModelAttribute("user") UserDTO user){
    System.out.println(user.getUserName() + " " + user.getPassword());
    return "index";
  }

}
