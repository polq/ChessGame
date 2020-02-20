package boardgame.items.figures.checkers;

import boardgame.items.cell.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

  Checker checker;

  @BeforeEach
  void init(){
    checker = new Checker(new Player("white",1), "");
  }
  @Test
  void move() {
    Cell startCell = new Cell('A', 1);
    Cell endCell = new Cell('B', 2);
    assertTrue(checker.move(startCell, endCell));
  }

  @Test
  void moveFail(){
    Cell startCell = new Cell('A', 1);
    Cell endCell = new Cell('B', 1);
    assertFalse(checker.move(startCell, endCell));
  }

  @Test
  void beat() {
    Cell startCell = new Cell('A', 1);
    Cell endCell = new Cell('C', 3);
    assertTrue(checker.beat(startCell, endCell));
  }

  @Test
  void beatFail() {
    Cell startCell = new Cell('A', 1);
    Cell endCell = new Cell('b', 2);
    assertFalse(checker.beat(startCell, endCell));
  }
}