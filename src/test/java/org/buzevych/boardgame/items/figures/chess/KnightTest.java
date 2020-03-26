package org.buzevych.boardgame.items.figures.chess;

import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.items.figures.chess.Knight;
import org.buzevych.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

  Knight knight;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    knight = new Knight(new Player("white"));
  }

  @Test
  void testGMove() {
    startCell = new Cell.Builder('d', 4).build();
    endCell = new Cell.Builder('b', 5).build();
    assertTrue(knight.canMove(startCell, endCell));
  }

  @Test
  void testMoveForward() {
    startCell = new Cell.Builder('a', 1).build();
    endCell = new Cell.Builder('a', 2).build();
    assertFalse(knight.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideWays() {
    startCell = new Cell.Builder('a', 2).build();
    endCell = new Cell.Builder('b', 2).build();
    assertFalse(knight.canMove(startCell, endCell));
  }

  @Test
  void testMoveDiagonal() {
    startCell = new Cell.Builder('a', 2).build();
    endCell = new Cell.Builder('b', 3).build();
    assertFalse(knight.canMove(startCell, endCell));
  }
}
