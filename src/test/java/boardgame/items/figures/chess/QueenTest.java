package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

  Queen queenChess;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  public void init() {
    queenChess = new Queen(new Player("white"));
  }

  @Test
  public void testMove() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('e', 7).getResultCell();
    assertTrue(queenChess.canMove(startCell, endCell));

    startCell = new CellBuilder('a', 7).getResultCell();
    endCell = new CellBuilder('b', 6).getResultCell();
    assertTrue(queenChess.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('a', 10).getResultCell();
    assertTrue(queenChess.canMove(startCell, endCell));

    startCell = new CellBuilder('A', 1).getResultCell();
    endCell = new CellBuilder('B', 3).getResultCell();
    assertFalse(queenChess.canMove(startCell, endCell));

    startCell = new CellBuilder('B', 4).getResultCell();
    endCell = new CellBuilder('A', 3).getResultCell();
    assertTrue(queenChess.canMove(startCell, endCell));
  }
}
