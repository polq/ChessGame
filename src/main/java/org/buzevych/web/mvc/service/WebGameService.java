package org.buzevych.web.mvc.service;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.google.common.annotations.VisibleForTesting;
import org.buzevych.core.boardgame.game.CheckersGameAI;
import org.buzevych.core.boardgame.game.ChessGameAI;
import org.buzevych.core.boardgame.game.GameAI;
import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.core.boardgame.game.GameStarter;
import org.buzevych.core.boardgame.gamesaver.FileGameStateSaver;
import org.buzevych.core.boardgame.gamesaver.GameStateSaver;
import org.buzevych.core.boardgame.gamesaver.JDBCGameStateSaver;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * {@link Service} class that is used to handle request from a {@link
 * org.springframework.stereotype.Controller} and perform the appropriate commands.
 */
@Service
public class WebGameService {

  private GameStarter starter;

  /**
   * Method that is used to start game based on the received parameters.
   *
   * @param gameName represents game that should be started
   * @param sourceType represents where the data should be stored
   * @param newGame defines if the game is new or it should be loaded from a latest save.
   * @return {@link GameSnapshot} representing a state of the created game.
   * @throws IllegalArgumentException in case there is not latest save and game is not new, or in
   *     case game is damaged.
   */
  public GameSnapshot startGame(String gameName, String sourceType, boolean newGame) {
    GameAI gameAI = gameAIByString(gameName);
    GameStateSaver stateSaver = gameSaverByString(sourceType, gameName);
    if (!newGame && !stateSaver.latestSave()) {
      throw new IllegalArgumentException("No last save found for " + gameName + " game");
    }
    GameStarter starter =
        new GameStarter.Builder()
            .withGameAI(gameAI)
            .withGameSaver(stateSaver)
            .newGame(newGame)
            .build();

    GameSnapshot snapshot = starter.getStartedGameSnap();
    if (snapshot.getBoard() == null) {
      throw new IllegalArgumentException(snapshot.getGameStatusMessage());
    }
    this.starter = starter;
    return snapshot;
  }

  /**
   * Method that is used to load a game from the specified file, loaded from a user.
   *
   * @param gameName represents a game that should be loaded.
   * @param fileName file that contains save
   * @return {@link GameSnapshot} representing a state of the created game.
   * @throws IOException in case specified file does not contain an appropriate save
   */
  public GameSnapshot loadGameFromFile(String gameName, MultipartFile fileName) throws IOException {
    GameAI gameAI = gameAIByString(gameName);
    File file = new File(Objects.requireNonNull(fileName.getOriginalFilename()));
    fileName.transferTo(file);
    FileGameStateSaver stateSaver = new FileGameStateSaver(file.toPath(), gameName);
    GameStarter starter =
        new GameStarter.Builder().withGameAI(gameAI).withGameSaver(stateSaver).build();
    GameSnapshot snapshot = starter.getStartedGameSnap();
    if (snapshot.getBoard() == null) {
      throw new IllegalArgumentException(snapshot.getGameStatusMessage());
    }
    this.starter = starter;
    return snapshot;
  }

  /**
   * Method that is used to make a move based on the received {@link String} parameter.
   *
   * @param move representing a move to be performed.
   * @return {@link GameSnapshot} representing a state of the game after performing a move.
   * @throws NullPointerException in case that game was not started before performing a move.
   */
  public GameSnapshot play(String move) {
    return starter.play(move);
  }

  private GameAI gameAIByString(String gameName) {
    if (gameName.equals("Chess")) {
      return new ChessGameAI();
    } else if (gameName.equals("Checkers")) {
      return new CheckersGameAI();
    } else {
      throw new IllegalArgumentException("No such game defined " + gameName);
    }
  }

  private GameStateSaver gameSaverByString(String sourceType, String gameName) {
    if (sourceType.equals("File")) {
      return new FileGameStateSaver(gameName);
    } else if (sourceType.equals("Database")) {
      return new JDBCGameStateSaver(gameName);
    } else {
      throw new IllegalArgumentException("Invalid data source type " + sourceType);
    }
  }

  @VisibleForTesting
  void setStarter(GameStarter starter) {
    this.starter = starter;
  }
}
