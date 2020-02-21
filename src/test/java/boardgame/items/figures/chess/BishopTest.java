package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
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
    bishop = new Bishop(new Player("black"), "");
  }

  @Test
  void move() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertFalse(bishop.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('a', 2);
    assertFalse(bishop.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('D', 3);
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('d', 1);
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('F', 3);
    assertTrue(bishop.canMove(startCell, endCell));

    startCell = new Cell('a', 7);
    endCell = new Cell('c', 6);
    assertFalse(bishop.canMove(startCell, endCell));
  }
}
