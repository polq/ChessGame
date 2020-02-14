package chess.game;

import chess.rules.ImaginaryGameRule;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  Game game;

  @BeforeEach
  public void init() {
    game = new Game();
    game.startNewGame(new ImaginaryGameRule());
  }

  @Test
  public void checkValidInput() {
    assertTrue(game.checkValidInput("A1 A3"));
    assertTrue(game.checkValidInput("a1-B2"));
    assertTrue(game.checkValidInput("b1/b2"));
    assertFalse(game.checkValidInput("H9.B2"));
    assertFalse(game.checkValidInput("H7 I4"));
    assertTrue(game.checkValidInput("a1-b2"));
    assertTrue(game.checkValidInput("c4-b4"));
  }

  @Test
  public void testPlay() {
    assertThrows(NullPointerException.class, () -> game.play(null));
    assertThrows(IllegalArgumentException.class, () -> game.play("H9.B2"));
    assertThrows(IllegalArgumentException.class, () -> game.play("test"));

    assertDoesNotThrow(() -> game.play("A1 A2"));
    assertDoesNotThrow(() -> game.play("A4 A3"));
  }

  @Test
  void testStartNewGame() {
    assertThrows(NullPointerException.class, () -> game.startNewGame(null));
    game.startNewGame(new ImaginaryGameRule());
    assertNotNull(game.getGameBoard());
    assertNotNull(game.getGameState());
    assertEquals(16, game.getGameBoard().getBoardCells().size());

    game.startNewGame(new StandardChessRule());
    assertEquals(64, game.getGameBoard().getBoardCells().size());
  }
}
