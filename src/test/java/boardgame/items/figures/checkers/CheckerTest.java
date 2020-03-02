package boardgame.items.figures.checkers;

import boardgame.items.board.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

  Checker checker;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    checker = new Checker(new Player("white", 1));
  }

  @Test
  void testMove() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 2).build();
    assertTrue(checker.canMove(startCell, endCell));
  }

  @Test
  void testMoveFail() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 1).build();
    assertFalse(checker.canMove(startCell, endCell));
  }

  @Test
  void testBeat() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('C', 3).build();
    assertTrue(checker.canBeat(startCell, endCell));
  }

  @Test
  void testBeatFail() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 2).build();
    assertFalse(checker.canBeat(startCell, endCell));
  }
}
