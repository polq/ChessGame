package org.buzevych.web.rest.service;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.buzevych.core.boardgame.game.*;
import org.buzevych.core.boardgame.gamesaver.JDBCGameStateSaver;
import org.buzevych.web.rest.model.UserGame;
import org.buzevych.web.rest.repository.UserGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class that is used to handle main game requests, such as starting a new game by it's name for a
 * specific user, to make a move in the specific game and to load game by it's gameid.
 */
@Service
@Slf4j
public class RestGameService {

  private UserGameRepository userGameRepository;
  private GameStarter starter;

  @Autowired
  public RestGameService(@Autowired UserGameRepository userGameRepository) {
    this.userGameRepository = userGameRepository;
  }

  public String startNewGame(String gameName, String username) {
    GameAI gameAI = gameAIByString(gameName);
    JDBCGameStateSaver saver = new JDBCGameStateSaver(gameName);
    starter =
        new GameStarter.Builder().withGameAI(gameAI).withGameSaver(saver).newGame(true).build();
    starter.getStartedGameSnap();
    String gameID = saver.getGameID();
    UserGame userGame = new UserGame(gameID, username, gameName);
    userGameRepository.save(userGame);
    log.info("New game for '{}' user with the game_id '{}' has been created", username, gameID);
    return gameID;
  }

  public GameSnapshot play(String move, String gameID, String username) {
    UserGame userGame = userGameRepository.findByGameID(gameID);
    if (!userGame.getUsername().equals(username)) {
      log.error("User with the username '{}' does not own '{}' game", username, gameID);
      throw new IllegalArgumentException(
          "Game " + gameID + " does not belong to the " + username + " user.");
    }
    String gameName = userGame.getGameName();
    starter =
        new GameStarter.Builder()
            .withGameAI(gameAIByString(gameName))
            .withGameSaver(new JDBCGameStateSaver(gameName, gameID))
            .newGame(false)
            .build();

    starter.getStartedGameSnap();
    GameSnapshot resultMoveSnapshot = starter.play(move);
    log.info("User {} has successfully made a {} move", userGame.getUsername(), move);
    return resultMoveSnapshot;
  }

  public GameSnapshot load(String gameId, String username) {
    UserGame userGame = userGameRepository.findByGameID(gameId);
    if (!userGame.getUsername().equals(username)) {
      log.error("GameID '{}' does not belong to a '{} user", gameId, username);
      throw new IllegalArgumentException("User " + username + " does not own " + gameId);
    }
    String gameName = userGame.getGameName();
    starter =
        new GameStarter.Builder()
            .withGameAI(gameAIByString(gameName))
            .withGameSaver(new JDBCGameStateSaver(gameName, gameId))
            .newGame(false)
            .build();
    GameSnapshot resultLoadedSnapShot = starter.getStartedGameSnap();
    log.info("Game with the '{}' gameID has been successfully loaded", gameId);
    return resultLoadedSnapShot;
  }

  private GameAI gameAIByString(String gameName) {
    if (gameName.equals("chess")) {
      return new ChessGameAI();
    } else if (gameName.equals("checkers")) {
      return new CheckersGameAI();
    } else {
      throw new IllegalArgumentException("No such game defined " + gameName);
    }
  }

  @VisibleForTesting
  public void setStarter(GameStarter starter) {
    this.starter = starter;
  }
}
