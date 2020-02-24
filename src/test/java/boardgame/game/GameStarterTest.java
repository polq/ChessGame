package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameStarterTest {

  GameStarter gameStarter;

  @Test
  void startNewGame() {
    gameStarter = GameStarter.startNewGame(new CheckersGameAI());
    assertNotNull(gameStarter);
    assertTrue(gameStarter.play("B3 a4").isActive());
  }

  @Test
  void playWithoutStartingGame() {
    assertThrows(NullPointerException.class, () -> gameStarter.play("A1 A2"));
  }

  @Test
  public void playInvalidCoordinates() {
    gameStarter = GameStarter.startNewGame(new CheckersGameAI());
    GameSnapshot gameSnapshot = gameStarter.play("A1 I9");
    assertEquals(
        "Invalid move coordinates, input coordinates should be in range of board size: 8x8",
        gameSnapshot.getStringGameSnap());
  }

  @Test
  public void play() {
    gameStarter = GameStarter.startNewGame(new CheckersGameAI());
    assertDoesNotThrow(() -> gameStarter.play("B3 A4"));
  }

  @Test
  void getStartedGameSnap() {
    gameStarter = GameStarter.startNewGame(new ChessGameAI());
    GameSnapshot snapshot = gameStarter.getStartedGameSnap();
    assertNotNull(snapshot);
    assertTrue(snapshot.isActive());
  }
}