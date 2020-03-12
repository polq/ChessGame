package org.buzevych.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import org.buzevych.boardgame.game.CheckersGameAI;
import org.buzevych.boardgame.game.ChessGameAI;
import org.buzevych.boardgame.game.GameAI;
import org.buzevych.boardgame.game.GameSnapshot;
import org.buzevych.boardgame.game.GameStarter;
import org.buzevych.boardgame.gamesaver.FileGameStateSaver;
import org.buzevych.boardgame.gamesaver.GameStateSaver;
import org.buzevych.boardgame.gamesaver.JDBCGameStateSaver;
import org.buzevych.boardgame.items.figures.checkers.Checker;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GameStarterService {

  private GameStarter starter;

  public GameSnapshot startGame(String gameName, String sourceType, boolean newGame) {
    GameAI gameAI = gameAIByString(gameName);
    GameStateSaver stateSaver = gameSaverByString(sourceType, gameName);
    if(!newGame && !stateSaver.latestSave()){
      throw new IllegalArgumentException("No last save found for " + gameName + " game");
    }
    GameStarter starter = new GameStarter.Builder()
        .withGameAI(gameAI)
        .withGameSaver(stateSaver)
        .newGame(newGame)
        .build();

    this.starter = starter;
    return starter.getStartedGameSnap();
  }

  public GameSnapshot loadGameFromFile(String gameName, MultipartFile fileName) throws IOException {
    GameAI gameAI = gameAIByString(gameName);
    File file = new File(Objects.requireNonNull(fileName.getOriginalFilename()));
    fileName.transferTo(file);
    FileGameStateSaver stateSaver = new FileGameStateSaver(file.toPath(), gameName);
    GameStarter starter = new GameStarter.Builder()
        .withGameAI(gameAI)
        .withGameSaver(stateSaver)
        .build();

    this.starter = starter;
    return starter.getStartedGameSnap();
  }

  public GameSnapshot play(String move) {
    return starter.play(move);
  }


  private GameAI gameAIByString(String gameName){
    if(gameName.equals("Chess")){
      return new ChessGameAI();
    } else if (gameName.equals("Checkers")){
      return new CheckersGameAI();
    } else {
      throw new IllegalArgumentException("No such game defined " + gameName);
    }
  }

  private GameStateSaver gameSaverByString(String sourceType, String gameName){
    if(sourceType.equals("File")){
      return new FileGameStateSaver(gameName);
    } else if (sourceType.equals("Database")){
      return new JDBCGameStateSaver(gameName);
    } else {
      throw  new IllegalArgumentException("Invalid data source type " + sourceType);
    }
  }
}
