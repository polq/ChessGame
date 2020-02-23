package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

  Knight knight;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    knight = new Knight(new Player("white"));
  }

  @Test
  void move() {
    startCell = new CellBuilder('d', 4).getResultCell();
    endCell = new CellBuilder('b', 5).getResultCell();
    assertTrue(knight.canMove(startCell, endCell));

    startCell = new CellBuilder('d', 4).getResultCell();
    endCell = new CellBuilder('F', 3).getResultCell();
    assertTrue(knight.canMove(startCell, endCell));

    startCell = new CellBuilder('d', 4).getResultCell();
    endCell = new CellBuilder('E', 2).getResultCell();
    assertTrue(knight.canMove(startCell, endCell));

    startCell = new CellBuilder('d', 4).getResultCell();
    endCell = new CellBuilder('c', 3).getResultCell();
    assertFalse(knight.canMove(startCell, endCell));

    startCell = new CellBuilder('d', 4).getResultCell();
    endCell = new CellBuilder('a', 4).getResultCell();
    assertFalse(knight.canMove(startCell, endCell));
  }
}
