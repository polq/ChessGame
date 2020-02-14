package chess.items.board;

import chess.items.chesspieces.king.King;
import chess.player.WhitePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

  @Test
  void testEquals() {
    Cell cellOne = new Cell('A', 1, null, true);
    Cell cellTwo = new Cell('A', 1, null, false);
    Cell cellThree = new Cell('A', 2, null, true);

    assertTrue(cellOne.equals(cellTwo));
    assertFalse(cellOne.equals(cellThree));
    assertFalse(cellTwo.equals(cellThree));
  }
}
