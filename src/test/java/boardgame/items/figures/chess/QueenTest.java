package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
import boardgame.items.figures.chess.Queen;
import boardgame.player.WhitePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

  Queen queenChess;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  public void init() {
    queenChess = new Queen(new WhitePlayer(), "");
  }

  @Test
  public void testMove() {
    startCell = new Cell('E', 2);
    endCell = new Cell('e', 7);
    assertTrue(queenChess.move(startCell, endCell));

    startCell = new Cell('a', 7);
    endCell = new Cell('b', 6);
    assertTrue(queenChess.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('a', 10);
    assertTrue(queenChess.move(startCell, endCell));

    startCell = new Cell('A', 1);
    endCell = new Cell('B', 3);
    assertFalse(queenChess.move(startCell, endCell));

    startCell = new Cell('B', 4);
    endCell = new Cell('A', 3);
    assertTrue(queenChess.move(startCell, endCell));
  }
}
