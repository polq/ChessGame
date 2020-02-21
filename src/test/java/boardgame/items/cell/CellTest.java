package boardgame.items.cell;

import boardgame.items.boardcell.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

  @Test
  void testEquals() {
    Cell cellOne = new Cell('A', 1, null, true);
    Cell cellTwo = new Cell('A', 1, null, false);
    Cell cellThree = new Cell('A', 2, null, true);

    assertEquals(cellOne, cellTwo);
    assertNotEquals(cellOne, cellThree);
    assertNotEquals(cellTwo, cellThree);
  }
}
