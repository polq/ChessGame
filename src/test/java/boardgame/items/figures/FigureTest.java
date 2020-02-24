package boardgame.items.figures;

import boardgame.items.board.Cell;
import boardgame.items.figures.chess.King;
import boardgame.player.WhitePlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {

  @Test
  void move() {
    Figure figure = new King(new WhitePlayer(), "");
    Cell cell = new Cell('A', 1);

    assertThrows(NullPointerException.class, () -> figure.move(null, null));
    assertThrows(IllegalArgumentException.class, () -> figure.move(cell, cell));
  }
}
