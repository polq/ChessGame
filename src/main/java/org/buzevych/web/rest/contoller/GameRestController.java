package org.buzevych.web.rest.contoller;

import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.web.rest.dto.BoardResponseDTO;
import org.buzevych.web.rest.dto.MoveDTO;
import org.buzevych.web.rest.service.RestGameService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
/**
 * Class that defines a main controller functionality for a game, which includes method to start a
 * new game, to make a move in a specified game and to load a game by it's id. All requests defined
 * in this class must be authorized, by including the following Key-Value pair in the HTTP header of
 * the request:
 *
 * <p>Authorization - Bearer_{token}
 *
 * <p>where token - is token obtained from a login defined in the {@link AuthRestController} class
 */
@RestController
@RequestMapping("/game")
public class GameRestController {

  RestGameService gameService;

  @Autowired
  public GameRestController(RestGameService gameService) {
    this.gameService = gameService;
  }

  /**
   * GET method that is used to start a new game, by defining the game name in the request URL.
   *
   * @param gameName defines which game should be started.
   * @return the following JSON response with the gameID that was created. { "gameID":
   *     "2020-03-26T12:11:09.283233", "created": "yes" }
   */
  @GetMapping(value = "/new/{gameName}")
  public ResponseEntity startNewGame(
      @PathVariable(name = "gameName") String gameName, Authentication authentication) {
    String username = authentication.getName();
    String gameID = gameService.startNewGame(gameName, username);
    Map<Object, Object> response = new HashMap<>();
    response.put("created", "yes");
    response.put("gameID", gameID);
    return ResponseEntity.ok(response);
  }

  /**
   * Method that is used to make a move for a defined game
   *
   * @param moveDTO specifies input param, that should be in the following format: {
   *     "gameId":"2020-03-26T12:11:09.283233", "move": "E1 E2" }
   * @return the state of the game in the following format: { "active": true, "status": "It's BLACK
   *     turn.", "board": { "E1": "WHITE_KING", "E2": "empty", "E3": "empty", "E4": "empty", ... } }
   */
  @PostMapping(value = "move")
  public ResponseEntity<BoardResponseDTO> makeMove(
      @RequestBody MoveDTO moveDTO, Authentication authentication) {
    String username = authentication.getName();
    GameSnapshot resultSnapshot =
        gameService.play(moveDTO.getMove(), moveDTO.getGameId(), username);

    BoardResponseDTO boardResponseDTO = BoardResponseDTO.getInstance(resultSnapshot);
    return ResponseEntity.ok(boardResponseDTO);
  }

  /**
   * Method that is used to load game by a game Id.
   *
   * <p>Request body should be in the following format: { "gameId":"2020-03-26T12:11:09.283233" }
   *
   * @return the state of the game in the following format: { "active": true, "status": "It's BLACK
   *     turn.", "board": { "E1": "WHITE_KING", "E2": "empty", "E3": "empty", "E4": "empty", ... } }
   */
  @PostMapping(value = "load")
  public ResponseEntity<BoardResponseDTO> load(
      @RequestBody String jsonString, Authentication authentication) {
    JSONObject jsonObject = new JSONObject(jsonString);
    String gameId = jsonObject.getString("gameId");
    String username = authentication.getName();

    GameSnapshot resultSnapshot = gameService.load(gameId, username);
    BoardResponseDTO boardResponseDTO = BoardResponseDTO.getInstance(resultSnapshot);
    return ResponseEntity.ok(boardResponseDTO);
  }
}
