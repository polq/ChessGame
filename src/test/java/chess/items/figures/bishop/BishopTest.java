package chess.items.figures.bishop;

import chess.items.board.Cell;
import chess.items.figures.chess.Bishop;
import chess.player.BlackPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

  Bishop bishop;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  public void init() {
    bishop = new Bishop(new BlackPlayer(), "");
  }

  @Test
  void move() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertFalse(bishop.move(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('a', 2);
    assertFalse(bishop.move(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('D', 3);
    assertTrue(bishop.move(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('d', 1);
    assertTrue(bishop.move(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('F', 3);
    assertTrue(bishop.move(startCell, endCell));

    startCell = new Cell('a', 7);
    endCell = new Cell('c', 6);
    assertFalse(bishop.move(startCell, endCell));
  }
}
