package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

  Bishop bishop;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  public void init() {
    bishop = new Bishop(new Player("black"));
  }

  @Test
  void move() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 4).getResultCell();
    assertFalse(bishop.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('a', 2).getResultCell();
    assertFalse(bishop.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('D', 3).getResultCell();
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('d', 1).getResultCell();
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('F', 3).getResultCell();
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new CellBuilder('a', 7).getResultCell();
    endCell = new CellBuilder('c', 6).getResultCell();
    assertFalse(bishop.canMove(startCell, endCell));
  }
}
