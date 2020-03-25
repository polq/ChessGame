package org.buzevych.web.rest.contoller;

import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.web.rest.dto.UserDTO;
import org.buzevych.web.rest.service.RestGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/game")
public class GameRestController {

  @Autowired RestGameService gameService;

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

  @PostMapping(value = "/move")
  public ResponseEntity makeMove(Authentication authentication) {

    return ResponseEntity.ok(true);
  }

  @PostMapping(value = "load")
  public ResponseEntity load(@RequestParam(name = "gameId") String gameId, Authentication authentication){

    String username = authentication.getName();
    GameSnapshot resultSnapshot = gameService.load(gameId, username);

    Map<Object, Object> response = new HashMap<>();
    response.put("active", resultSnapshot.isActive());
    response.put("status", resultSnapshot.getGameStatusMessage());
    response.put(
        "board",
            new ArrayList<>(resultSnapshot.getBoard().getBoardCells().values()));
    return ResponseEntity.ok(response);
  }
}
