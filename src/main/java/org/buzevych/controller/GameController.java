package org.buzevych.controller;

import java.io.File;
import java.io.IOException;
import org.buzevych.boardgame.game.GameSnapshot;
import org.buzevych.service.GameStarterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

  @Autowired
  GameStarterService service;

  @PostMapping(value = "/start", params = "startGame")
  public String startNewGame(@ModelAttribute(value = "gameName") String gameName,
      @ModelAttribute(value = "sourceType") String sourceType,
      Model model) {
    GameSnapshot snapshot = service.startGame(gameName, sourceType, true);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

  @PostMapping(value = "/start", params = "loadGame")
  public String loadSave(@ModelAttribute(value = "gameName") String gameName,
      @ModelAttribute(value = "sourceType") String sourceType,
      @RequestParam("file") MultipartFile file,
      Model model) throws IOException {
    GameSnapshot snapshot;
    if (!file.isEmpty()) {
      snapshot = service.loadGameFromFile(gameName, file);
    } else {
      snapshot = service.startGame(gameName, sourceType, false);
    }
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

  @GetMapping("/")
  public String index() {
    return "index";
  }

  @PostMapping("/play")
  public String makeMove(@ModelAttribute(value = "move") String move, Model model) {
    GameSnapshot snapshot = service.play(move);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }
}
