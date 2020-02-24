package boardgame.items.figures.checkers;

import boardgame.items.board.Cell;
import boardgame.player.WhitePlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerKingTest {

  CheckerKing king;
  @BeforeEach
  void init() {
    king = new CheckerKing(new WhitePlayer(), "");
  }

  @Test
  void move() {
    Cell startCell = new Cell('A', 1);
    Cell endCell = new Cell('D', 4);
    assertTrue(king.move(startCell, endCell));
  }

  @Test
  void moveFail() {
    Cell startCell = new Cell('B', 1);
    Cell endCell = new Cell('D', 4);
    assertFalse(king.move(startCell, endCell));
  }
}