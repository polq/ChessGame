package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
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
  void move() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 4).getResultCell();
    assertTrue(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('E', 2).getResultCell();
    assertFalse(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 1).getResultCell();
    endCell = new CellBuilder('E', 2).getResultCell();
    assertTrue(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('F', 1).getResultCell();
    endCell = new CellBuilder('E', 1).getResultCell();
    assertTrue(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('C', 3).getResultCell();
    endCell = new CellBuilder('B', 3).getResultCell();
    assertTrue(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('C', 2).getResultCell();
    assertFalse(rook.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 11).getResultCell();
    endCell = new CellBuilder('a', 2).getResultCell();
    assertTrue(rook.canMove(startCell, endCell));
  }
}
