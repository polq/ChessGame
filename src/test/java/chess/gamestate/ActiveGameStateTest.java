package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.rules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActiveGameStateTest {

  GameState normalGameState;

  @BeforeEach
  void init() {
    normalGameState = new ActiveGameState(new Board(new StandardChessRule()));
  }

  @Test
  void testExecuteCommandMove() {
    Cell initialE2Cell = normalGameState.getGameBoard().getBoardCells().get("E2");
    assertFalse(initialE2Cell.isEmpty());

    normalGameState.executeCommand("E2", "E4");
    Cell e2AfterExecute = normalGameState.getGameBoard().getBoardCells().get("E2");
    assertTrue(e2AfterExecute.isEmpty());
  }

  @Test
  void testExecuteCommand() {
    // trying to move pawn on occupied cell
    assertThrows(IllegalArgumentException.class, () -> normalGameState.executeCommand("E2", "D2"));
    // trying to just over pawn
    assertThrows(IllegalArgumentException.class, () -> normalGameState.executeCommand("A1", "A3"));
    // trying to castle with and occupied path
    assertThrows(IllegalArgumentException.class, () -> normalGameState.executeCommand("A1", "E1"));
    // trying to move black figures while it's white player's turn
    assertThrows(IllegalArgumentException.class, () -> normalGameState.executeCommand("A7", "A6"));
    // trying to make an illegal move
    assertThrows(IllegalArgumentException.class, () -> normalGameState.executeCommand("E2", "E5"));
  }
}
