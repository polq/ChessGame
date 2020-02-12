package chess.game;

import chess.gamestate.ActiveGameState;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

  Game game;

  @BeforeEach
  public void init() {
    game = new Game();
    game.startNewGame(new StandardChessRule());
  }

  @Test
  public void checkValidInput() {
    assertEquals(true, game.checkValidInput("A1 A3"));
    assertEquals(true, game.checkValidInput("a1-B2"));
    assertEquals(true, game.checkValidInput("b1/b2"));
    assertEquals(false, game.checkValidInput("H9.B2"));
    assertEquals(false, game.checkValidInput("H7 I4"));
    assertEquals(true, game.checkValidInput("h7-b4"));
    assertEquals(true, game.checkValidInput("h7-b4"));
  }

  @Test
  public void testPlay() {
    assertThrows(NullPointerException.class, () -> game.play(null));

    assertThrows(IllegalArgumentException.class, () -> game.play("H9.B2"));

    assertThrows(IllegalArgumentException.class, () -> game.play("test"));
  }

  @Test
  public void testGameJustStarted(){
    //assertEquals("In active", game.play("A2 A3"));
  }

  @Test
  public void testStateSwitching(){
    game.play("e2-e4");
    assertTrue(game.getGameState() instanceof ActiveGameState);
  }
}
