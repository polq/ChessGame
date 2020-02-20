package boardgame.items.figures.chess;

import boardgame.items.cell.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

  Knight knight;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    knight = new Knight(new Player("white"), "");
  }

  @Test
  void move() {
    startCell = new Cell('d', 4);
    endCell = new Cell('b', 5);
    assertTrue(knight.move(startCell, endCell));

    startCell = new Cell('d', 4);
    endCell = new Cell('F', 3);
    assertTrue(knight.move(startCell, endCell));

    startCell = new Cell('d', 4);
    endCell = new Cell('E', 2);
    assertTrue(knight.move(startCell, endCell));

    startCell = new Cell('d', 4);
    endCell = new Cell('c', 3);
    assertFalse(knight.move(startCell, endCell));

    startCell = new Cell('d', 4);
    endCell = new Cell('a', 4);
    assertFalse(knight.move(startCell, endCell));
  }

  @Test
  @Disabled
  void testToString() {
    System.out.println(knight);
  }
}
