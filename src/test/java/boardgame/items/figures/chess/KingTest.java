package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
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
    king = new King(new Player("white"), "");
  }

  @Test
  void move() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertFalse(king.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('E', 2);
    assertFalse(king.move(startCell, endCell));

    startCell = new Cell('E', 1);
    endCell = new Cell('E', 2);
    assertTrue(king.move(startCell, endCell));

    startCell = new Cell('F', 1);
    endCell = new Cell('E', 1);
    assertTrue(king.move(startCell, endCell));

    startCell = new Cell('C', 3);
    endCell = new Cell('B', 3);
    assertTrue(king.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('C', 2);
    assertFalse(king.move(startCell, endCell));
  }
}
