package org.buzevych.core.boardgame.items.figures.checkers;

import org.buzevych.core.boardgame.items.board.Cell;
import org.buzevych.core.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerKingTest {

  CheckerKing king;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    king = new CheckerKing(new Player("white"));
  }

  @Test
  void testMove() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('D', 4).build();
    assertTrue(king.canMove(startCell, endCell));
  }

  @Test
  void testMoveFail() {
    startCell = new Cell.Builder('B', 1).build();
    endCell = new Cell.Builder('D', 4).build();
    assertFalse(king.canMove(startCell, endCell));
  }
}
