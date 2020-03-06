package org.buzevych.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

  @PostMapping("/")
  public String startNewGame(@ModelAttribute(value = "props") GameProperties properties) {
    String gameName = properties.getGameName();
    return "index";
  }

  @GetMapping("/")
  public String index(Model model) {
    GameProperties properties = new GameProperties();
    properties.setGameName("Chess");
    model.addAttribute("props", properties);
    return "index";
  }
}
