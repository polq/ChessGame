package boardgame.items.boardcell;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.items.figures.Figure;
import boardgame.items.figures.chess.King;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CellBuilderTest {

  CellBuilder builder;
  Cell resultCell;

  @BeforeEach
  void init() {
    builder = new CellBuilder('A', 1);
  }

  @Test
  void buildEmptyCell() {
    builder.buildEmptyCell();
    resultCell = builder.getResultCell();
    assertTrue(resultCell.isEmpty());
  }

  @Test
  void buildFigureCell() {
    Figure figure = new King(new Player(""));
    builder.buildFigureCell(figure);
    resultCell = builder.getResultCell();
    assertNotNull(resultCell.getFigure());
  }

  @Test
  void buildChangAbleFigureCell() {
    Figure figure = new King(new Player(""));
    builder.buildChangeableFigureCell(figure);
    resultCell = builder.getResultCell();
    assertTrue(resultCell.isChangeable());
  }

  @Test
  void buildChangeAbleEmptyCell() {
    builder.buildChangeAbleEmptyCell();
    resultCell = builder.getResultCell();
    assertTrue(resultCell.isEmpty());
    assertTrue(resultCell.isChangeable());
  }
}
