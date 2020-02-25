package boardgame.items.boardcell;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.items.figures.chess.King;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellTest {

  Cell cell;
  CellBuilder cellBuilder;

  @BeforeEach
  void init() {
    cellBuilder = new CellBuilder('A', 1);
  }

  @Test
  void figureMovedFromThisCell() {
    cellBuilder.buildFigureCell(new King(new Player("white")));
    cell = cellBuilder.getResultCell();
    cell.figureMovedFromThisCell();
    assertTrue(cell.isEmpty());
  }

  @Test
  void figureMovedToThisCell() {
    cellBuilder.buildEmptyCell();
    cell = cellBuilder.getResultCell();
    cell.figureMovedToThisCell(new King(new Player("white")));
    assertEquals("KING", cell.getFigure().toString());
  }

  @Test
  void getStringKey() {
    cellBuilder.buildEmptyCell();
    cell = cellBuilder.getResultCell();
    assertEquals("A1", cell.getStringKey());
  }

  @Test
  void testEquals() {
    cellBuilder.buildEmptyCell();
    cell = cellBuilder.getResultCell();
    cellBuilder.buildChangeAbleEmptyCell();
    Cell anotherCell = cellBuilder.getResultCell();

    assertEquals(cell, anotherCell);
  }
}
