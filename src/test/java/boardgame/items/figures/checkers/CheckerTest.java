package boardgame.items.figures.checkers;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

  Checker checker;

  @BeforeEach
  void init() {
    checker = new Checker(new Player("white", 1));
  }

  @Test
  void move() {
    Cell startCell = new CellBuilder('A', 1).getResultCell();
    Cell endCell = new CellBuilder('B', 2).getResultCell();
    assertTrue(checker.canMove(startCell, endCell));
  }

  @Test
  void moveFail() {
    Cell startCell = new CellBuilder('A', 1).getResultCell();
    Cell endCell = new CellBuilder('B', 1).getResultCell();
    assertFalse(checker.canMove(startCell, endCell));
  }

  @Test
  void beat() {
    Cell startCell = new CellBuilder('A', 1).getResultCell();
    Cell endCell = new CellBuilder('C', 3).getResultCell();
    assertTrue(checker.canBeat(startCell, endCell));
  }

  @Test
  void beatFail() {
    Cell startCell = new CellBuilder('A', 1).getResultCell();
    Cell endCell = new CellBuilder('B', 2).getResultCell();
    assertFalse(checker.canBeat(startCell, endCell));
  }
}