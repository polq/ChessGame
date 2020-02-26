package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

  Rook rook;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    rook = new Rook(new Player("white"));
  }

  @Test
  void testMoveForward() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 7).build();
    assertTrue(rook.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideways() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('A', 2).build();
    assertTrue(rook.canMove(startCell, endCell));
  }

  @Test
  void testMoveDiagonal() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('D', 1).build();
    assertFalse(rook.canMove(startCell, endCell));
  }

  @Test
  void testMoveLikeKnight() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 3).build();
    assertFalse(rook.canMove(startCell, endCell));
  }
}
