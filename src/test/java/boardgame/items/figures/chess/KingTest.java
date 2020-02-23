package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
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
  void move() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 4).getResultCell();
    assertFalse(king.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('E', 2).getResultCell();
    assertFalse(king.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 1).getResultCell();
    endCell = new CellBuilder('E', 2).getResultCell();
    assertTrue(king.canMove(startCell, endCell));

    startCell = new CellBuilder('F', 1).getResultCell();
    endCell = new CellBuilder('E', 1).getResultCell();
    assertTrue(king.canMove(startCell, endCell));

    startCell = new CellBuilder('C', 3).getResultCell();
    endCell = new CellBuilder('B', 3).getResultCell();
    assertTrue(king.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('C', 2).getResultCell();
    assertFalse(king.canMove(startCell, endCell));
  }
}
