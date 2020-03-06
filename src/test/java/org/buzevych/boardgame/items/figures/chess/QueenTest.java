package org.buzevych.boardgame.items.figures.chess;

import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.items.figures.chess.Queen;
import org.buzevych.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

  Queen queenChess;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    queenChess = new Queen(new Player("white"));
  }

  @Test
  void testMoveForward() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 7).build();
    assertTrue(queenChess.canMove(startCell, endCell));
  }

  @Test
  void testMoveSideways() {
    startCell = new Cell.Builder('a', 1).build();
    endCell = new Cell.Builder('a', 10).build();
    assertTrue(queenChess.canMove(startCell, endCell));
  }

  @Test
  void testMoveDiagonal() {
    startCell = new Cell.Builder('E', 7).build();
    endCell = new Cell.Builder('D', 8).build();
    assertTrue(queenChess.canMove(startCell, endCell));
  }

  @Test
  void testMoveLikeKnight() {
    startCell = new Cell.Builder('A', 1).build();
    endCell = new Cell.Builder('B', 3).build();
    assertFalse(queenChess.canMove(startCell, endCell));
  }
}
