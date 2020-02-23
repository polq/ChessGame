package boardgame.items.figures.checkers;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerKingTest {

  CheckerKing king;

  @BeforeEach
  void init() {
    king = new CheckerKing(new Player("white"));
  }

  @Test
  void move() {
    Cell startCell = new CellBuilder('A', 1).getResultCell();
    Cell endCell = new CellBuilder('D', 4).getResultCell();
    assertTrue(king.canMove(startCell, endCell));
  }

  @Test
  void moveFail() {
    Cell startCell = new CellBuilder('B', 1).getResultCell();
    Cell endCell = new CellBuilder('D', 4).getResultCell();
    assertFalse(king.canMove(startCell, endCell));
  }
}