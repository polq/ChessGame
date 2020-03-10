package org.buzevych.controller;

import org.buzevych.boardgame.game.GameSnapshot;
import org.buzevych.service.GameStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

  @Autowired
  GameStarterService service;

  @PostMapping("/start")
  public String startNewGame(@ModelAttribute(value = "props") GameProperties properties,
      Model model) {
    String gameName = properties.getGameName();
    GameSnapshot snapshot = service.startNewGame(gameName);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

  @GetMapping("/")
  public String index(Model model) {
    GameProperties properties = new GameProperties();
    properties.setGameName("Chess");
    model.addAttribute("props", properties);
    return "index";
  }

  @GetMapping("/play")
  public String play(@RequestParam(value = "move", required = false) String move, Model model) {
    System.out.println(move);
    GameSnapshot snapshot = service.play(move);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

}
