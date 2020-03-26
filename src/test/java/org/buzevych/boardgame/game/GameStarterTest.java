package org.buzevych.boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import org.buzevych.boardgame.gamesaver.FileGameStateSaver;
import org.buzevych.boardgame.gamesaver.GameStateSaver;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

class GameStarterTest {

  GameStarter gameStarter;

  @Test
  void startNewGame() {
    gameStarter = new GameStarter.Builder().withGameAI(new CheckersGameAI()).build();
    assertNotNull(gameStarter);
    assertTrue(gameStarter.play("B3 a4").isActive());
  }

  @Test
  void playWithoutStartingGame() {
    assertThrows(NullPointerException.class, () -> gameStarter.play("A1 A2"));
  }

  @Test
  public void playInvalidCoordinates() {
    gameStarter = new GameStarter.Builder().withGameAI(new CheckersGameAI()).build();
    GameSnapshot gameSnapshot = gameStarter.play("A1 I9");
    assertNotNull(gameSnapshot);
  }

  @Test
  public void play() {
    gameStarter = new GameStarter.Builder().withGameAI(new CheckersGameAI()).build();
    assertDoesNotThrow(() -> gameStarter.play("B3 A4"));
  }

  @Test
  void getStartedGameSnapLoadGame() {
    File file = new File("any.txt");
    GameStateSaver saver = new FileGameStateSaver(file.toPath(), "chess");
    gameStarter =
        new GameStarter.Builder()
            .withGameAI(new ChessGameAI())
            .withGameSaver(saver)
            .newGame(true)
            .build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertNotNull(snapshot);
    assertTrue(snapshot.isActive());
    file.deleteOnExit();
  }

  @Test
  void getStartedGameSnapNewGame() {
    File file = new File("game.txt");
    GameStateSaver saver = new FileGameStateSaver(file.toPath(), "chess");
    gameStarter =
        new GameStarter.Builder()
            .withGameAI(new CheckersGameAI())
            .withGameSaver(saver)
            .newGame(true)
            .build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertTrue(snapshot.isActive());
    file.deleteOnExit();
  }

  @Test
  void getStartedGameSnapDamagedFile() {
    GameStateSaver saver = new FileGameStateSaver(Path.of("damaged.txt"), "chess");
    gameStarter =
        new GameStarter.Builder().withGameAI(new CheckersGameAI()).withGameSaver(saver).build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertFalse(snapshot.isActive());
  }
}
