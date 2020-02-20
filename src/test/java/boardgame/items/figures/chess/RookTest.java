package boardgame.items.figures.chess;

import boardgame.items.cell.Cell;
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
    rook = new Rook(new Player("white"), "");
  }

  @Test
  void move() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertTrue(rook.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('E', 2);
    assertFalse(rook.move(startCell, endCell));

    startCell = new Cell('E', 1);
    endCell = new Cell('E', 2);
    assertTrue(rook.move(startCell, endCell));

    startCell = new Cell('F', 1);
    endCell = new Cell('E', 1);
    assertTrue(rook.move(startCell, endCell));

    startCell = new Cell('C', 3);
    endCell = new Cell('B', 3);
    assertTrue(rook.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('C', 2);
    assertFalse(rook.move(startCell, endCell));

    startCell = new Cell('A', 11);
    endCell = new Cell('a', 2);
    assertTrue(rook.move(startCell, endCell));
  }
}
