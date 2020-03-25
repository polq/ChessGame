package org.buzevych.core.boardgame.items.figures.chess;

import org.buzevych.core.boardgame.items.board.Cell;
import org.buzevych.core.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

  Bishop bishop;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  public void init() {
    bishop = new Bishop(new Player("black"));
  }

  @Test
  void testMoveForward() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 4).build();
    assertFalse(bishop.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideWay() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('a', 4).build();
    assertFalse(bishop.canMove(startCell, endCell));
  }

  @Test
  void testMoveDiagonal() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('D', 3).build();
    assertTrue(bishop.canMove(startCell, endCell));
  }

  @Test
  void testMoveAtRandomPosition() {
    startCell = new Cell.Builder('a', 7).build();
    endCell = new Cell.Builder('c', 6).build();
    assertFalse(bishop.canMove(startCell, endCell));
  }
}
