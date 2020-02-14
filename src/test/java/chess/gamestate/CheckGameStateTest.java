package chess.gamestate;

import chess.items.board.Board;
import chess.player.WhitePlayer;
import chess.rules.CheckGameRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckGameStateTest {

  GameState gameState;

  @BeforeEach
  void init() {
    gameState = new CheckGameState(new Board(new CheckGameRule()));
  }

  @Test
  void textExecute() {
    // move to an checked spot
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand("A2", "A1"));
    // use different player figure
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand("B1", "A2"));
    // invalid coordinates
    assertThrows(NullPointerException.class, () -> gameState.executeCommand("A3", "B2"));
  }

  @Test
  void testExecute() {
    gameState.executeCommand("A2", "B1");
    assertEquals(
        WhitePlayer.class,
        gameState.getGameBoard().getBoardCells().get("B1").getFigure().getChessOwner().getClass());
  }
}
