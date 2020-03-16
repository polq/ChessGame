package org.buzevych.controller;

import java.io.IOException;
import org.buzevych.boardgame.game.GameSnapshot;
import org.buzevych.service.WebGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Spring MVC {@link Controller} that has appropriate methods to handle all main game GET and POST
 * request.
 */
@Controller
public class WebGameController {

  WebGameService service;

  @Autowired
  public WebGameController(WebGameService service) {
    this.service = service;
  }

  /**
   * Method that is used to start a new game by receiving POST request with the appropriate params
   * and returns a game page.
   *
   * @param gameName represents game that will be played. Available games specified in the
   *     games.properties file.
   * @param sourceType represents where all game data will be stored. Available source types
   *     specified in the * games.properties file.
   * @param model {@link Model} that will be used to draw return page
   * @return {@code game} page that will be used for further interacting with the game.
   */
  @PostMapping(value = "/start", params = "startGame")
  public String startNewGame(
      @ModelAttribute(value = "gameName") String gameName,
      @ModelAttribute(value = "sourceType") String sourceType,
      Model model) {
    GameSnapshot snapshot = service.startGame(gameName, sourceType, true);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

  /**
   * Method that is used to load a a game either for a latest save file or from a file uploaded by a
   * used by receiving POST request with the appropriate params * and returns a game page.
   *
   * @param gameName represents game that will be played. Available games specified in the
   *     games.properties file.
   * @param sourceType represents where all game data will be stored. Available source types
   *     specified in the * games.properties file.
   * @param model {@link Model} that will be used to draw return page
   * @param file specifies a file with the save that the used wants to upload.
   * @return {@code game} page that will be used for further interacting with the game.
   * @throws IOException in case file with the potential save is damaged or has some other problems.
   */
  @PostMapping(value = "/start", params = "loadGame")
  public String loadSave(
      @ModelAttribute(value = "gameName") String gameName,
      @ModelAttribute(value = "sourceType") String sourceType,
      @RequestParam("file") MultipartFile file,
      Model model)
      throws IOException {
    GameSnapshot snapshot;
    if (!file.isEmpty()) {
      snapshot = service.loadGameFromFile(gameName, file);
    } else {
      snapshot = service.startGame(gameName, sourceType, false);
    }
    model.addAttribute("snapshot", snapshot);
    return "game";
  }

  /**
   * Used to return game start page with the "index" name by "/" mapping.
   *
   * @return {@link String} representing index page.
   */
  @GetMapping("/")
  public String index() {
    return "index";
  }

  /**
   * Main method that is used to make a move in the board game. It has just one method that is
   * delegating received string to a service bean to perform necessary move.
   *
   * @param move {@link String} representing move coordinates
   * @param model {@link Model} that will be used to draw return page
   * @return @return {@code game} page that will be used for further interacting with the game.
   */
  @PostMapping("/play")
  public String makeMove(@ModelAttribute(value = "move") String move, Model model) {
    GameSnapshot snapshot = service.play(move);
    model.addAttribute("snapshot", snapshot);
    return "game";
  }
}
