package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.gamesaver.FileGameStateSaver;
import boardgame.gamesaver.GameStateSaver;
import org.junit.jupiter.api.Test;

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
    assertEquals(
        "Invalid move coordinates, input coordinates should be in range of board size: 8x8",
        gameSnapshot.getStringGameSnap());
  }

  @Test
  public void play() {
    gameStarter = new GameStarter.Builder().withGameAI(new CheckersGameAI()).build();
    assertDoesNotThrow(() -> gameStarter.play("B3 A4"));
  }

  @Test
  void getStartedGameSnapLoadGame() {
    GameStateSaver saver = new FileGameStateSaver(Path.of("damaged.txt"));
    gameStarter =
        new GameStarter.Builder().withGameAI(new ChessGameAI()).withGameSaver(saver).build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertNotNull(snapshot);
    assertTrue(snapshot.isActive());
  }

  @Test
  void getStartedGameSnapNewGame() {
    GameStateSaver saver = new FileGameStateSaver(Path.of("game.txt"), "chess");
    gameStarter =
        new GameStarter.Builder()
            .withGameAI(new CheckersGameAI())
            .withGameSaver(saver)
            .newGame(true)
            .build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertTrue(snapshot.isActive());
  }

  @Test
  void getStartedGameSnapDamagedFile() {
    GameStateSaver saver = new FileGameStateSaver(Path.of("damaged.txt"));
    gameStarter =
        new GameStarter.Builder().withGameAI(new CheckersGameAI()).withGameSaver(saver).build();
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertFalse(snapshot.isActive());
  }
}
