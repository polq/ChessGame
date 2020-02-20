package boardgame.game;

import boardgame.items.board.CheckersBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckersGameTest {

  Game checkers;

  @BeforeEach
  public void init() {
    checkers = new CheckersGame();
    checkers.startNewGame(new CheckersBoardFactory());
  }

  @Test
  public void checkValidInput() {
    assertTrue(checkers.checkValidInput("A2 A3 A5"));
    assertTrue(checkers.checkValidInput("a2-A3/A5-A7 A8 A1 H7"));
    assertFalse(checkers.checkValidInput("I1 A7"));
  }

  @Test
  public void startNewGame() {
    checkers.startNewGame(new CheckersBoardFactory());
    assertNotNull(checkers);
  }

  @Test
  public void startNewGameNullRule() {
    assertThrows(NullPointerException.class, () -> checkers.startNewGame(null));
  }

  @Test
  public void playNull() {
    assertThrows(NullPointerException.class, () -> checkers.play(null));
  }

  @Test
  public void playWithOutStartingGame() {
    checkers = new CheckersGame();
    assertThrows(NullPointerException.class, () -> checkers.play("A1 A2"));
  }

  @Test
  public void playInvalidCoordinates() {
    assertThrows(IllegalArgumentException.class, () -> checkers.play("A1 I9"));
  }

  @Test
  public void play() {
    assertDoesNotThrow(() -> checkers.play("B3 A4"));
  }

  @Test
  public void testToString(){
    assertEquals(checkers.getGameBoard().toString(), checkers.toString());
  }
}
