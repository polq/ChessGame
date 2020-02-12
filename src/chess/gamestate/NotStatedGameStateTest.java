package chess.gamestate;

import chess.items.board.Board;
import chess.items.chesspieces.pawn.Pawn;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotStatedGameStateTest {

  GameState state;

  @BeforeEach
  public void init() {
    Board gameBoard = new Board(new StandardChessRule());
    state = new NotStartedGameState(gameBoard);
  }

  @Test
  public void executeCommand() {
    state.executeCommand("E2", "E4");
    assertEquals(
        Pawn.class,
        ((Pawn) (state.getGameBoard().getBoardCells().get("E4").getFigure())).getClass());

    assertEquals(
        "\u2659", state.getGameBoard().getBoardCells().get("E4").getFigure().getChessIcon());

    
  }

  @Test
  public void testAnotherFigure() {
    assertThrows(IllegalCallerException.class, () -> state.executeCommand("E7", "E6"));
  }

  @Test
  public void testInputCells() {
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("E2", "E1"));
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("E3", "E4"));
  }

  @Test
  public void testFigureMovement(){
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("E2", "E5"));
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("B1", "B3"));
  }

  @Test
  public void testPathing(){
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("A1", "A3"));
  }


}
