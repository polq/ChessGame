package chess.items.chesspieces;

import chess.items.board.Cell;
import chess.items.chesspieces.king.King;
import chess.player.WhitePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessFigureTest {

  @Test
  void move() {
    ChessFigure chessFigure = new King(new WhitePlayer(), "");
    Cell cell = new Cell('A', 1);

    assertThrows(NullPointerException.class, () -> chessFigure.move(null, null));
    assertThrows(IllegalArgumentException.class, () -> chessFigure.move(cell, cell));
  }
}
