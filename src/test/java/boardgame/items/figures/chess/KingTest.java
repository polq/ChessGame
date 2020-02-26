package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

  King king;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    king = new King(new Player("white"));
  }

  @Test
  void testMoveForwardDiagonal() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 2).build();
    assertTrue(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveForwardDiagonalFail() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('C', 3).build();
    assertFalse(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveForward() {
    startCell = new Cell.Builder('E', 1).build();
    endCell = new Cell.Builder('E', 2).build();
    assertTrue(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveForwardFail() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 4).build();
    assertFalse(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideWays() {
    startCell = new Cell.Builder('E', 1).build();
    endCell = new Cell.Builder('F', 1).build();
    assertTrue(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideWaysFail() {
    startCell = new Cell.Builder('E', 1).build();
    endCell = new Cell.Builder('A', 1).build();
    assertFalse(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveAtRandomPosition() {
    startCell = new Cell.Builder('a', 1).build();
    endCell = new Cell.Builder('E', 2).build();
    assertFalse(king.canMove(startCell, endCell));
  }
}
