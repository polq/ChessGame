package org.buzevych.service;

import org.buzevych.boardgame.game.ChessGameAI;
import org.buzevych.boardgame.game.GameSnapshot;
import org.buzevych.boardgame.game.GameStarter;
import org.buzevych.boardgame.gamesaver.FileGameStateSaver;
import org.springframework.stereotype.Service;

@Service
public class GameStarterService {

  private GameStarter starter;

  public GameSnapshot startNewGame(String string) {
    GameStarter starter = new GameStarter.Builder()
        .withGameAI(new ChessGameAI())
        .withGameSaver(new FileGameStateSaver("chess"))
        .newGame(true)
        .build();

    this.starter = starter;
    return starter.getStartedGameSnap();
  }

  public GameSnapshot play(String move) {
    return starter.play(move);
  }
}
