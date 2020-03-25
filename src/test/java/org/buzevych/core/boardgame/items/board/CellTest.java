package org.buzevych.core.boardgame.items.board;

import static org.junit.jupiter.api.Assertions.*;

import org.buzevych.core.boardgame.items.figures.chess.King;
import org.buzevych.core.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {

  Cell cell;
  Cell.Builder cellBuilder;

  @BeforeEach
  void init() {
    cellBuilder = new Cell.Builder('A', 1);
  }

  @Test
  void figureMovedFromThisCell() {
    cell = cellBuilder.withFigure(new King(new Player("white"))).build();
    cell.figureMovedFromThisCell();
    assertTrue(cell.isEmpty());
  }

  @Test
  void figureMovedToThisCell() {
    cell = cellBuilder.empty(true).build();
    cell.figureMovedToThisCell(new King(new Player("white")));
    assertEquals("KING", cell.getFigure().toString());
  }

  @Test
  void getStringKey() {
    cell = cellBuilder.empty(true).build();
    assertEquals("A1", cell.getStringKey());
  }

  @Test
  void testEquals() {
    cell = cellBuilder.empty(true).build();
    Cell anotherCell =
        cellBuilder.withFigure(new King(new Player("white"))).changeable(true).build();

    assertEquals(cell, anotherCell);
  }
}
