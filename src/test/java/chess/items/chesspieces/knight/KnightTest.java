package chess.items.chesspieces.knight;

import chess.items.board.Cell;
import chess.player.WhitePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

  Knight knight;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    knight = new Knight(new WhitePlayer(), "");
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
  void testToString() {
    System.out.println(knight);
  }
}
