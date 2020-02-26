package boardgame.items.board;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.items.figures.Figure;
import boardgame.items.figures.chess.King;
import boardgame.player.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  BoardFactory factory;
  Board board;

  @BeforeEach
  void init() {
    board = BoardFactory.createBoard("checkers", (LinkedList<Player>) (Arrays.asList(new Player("white"), new Player("black"))));
  }

  @Test
  void getCellList() {
    String[] coordinates = new String[] {"A1", "B2"};
    List<Cell> cells = board.getCellList(coordinates);
    assertEquals(2, cells.size());
  }

  @Test
  void getIllegalCellList() {
    String[] coordinates = new String[] {"A1", "O2"};
    assertThrows(IllegalArgumentException.class, () -> board.getCellList(coordinates));
  }

  @Test
  void moveFigure() {
    Cell fromCell = board.getBoardCells().get("B3");
    Cell toCell = board.getBoardCells().get("A4");
    board.moveFigures(fromCell, toCell);
    assertTrue(fromCell.isEmpty());
  }

  @Test
  void beatFigure() {
    Cell fromCell = board.getBoardCells().get("B3");
    Figure fromFigure = fromCell.getFigure();
    Cell toCell = board.getBoardCells().get("A8");
    board.moveFigures(fromCell, toCell);
    assertEquals(fromFigure, toCell.getFigure());
  }

  @Test
  void swapFigures() {
    Cell fromCell = board.getBoardCells().get("B3");
    Cell toCell = board.getBoardCells().get("D3");
    board.moveFigures(fromCell, toCell);
    assertEquals(fromCell.getFigure().getFigureOwner(), toCell.getFigure().getFigureOwner());
  }

  @Test
  void deleteFigureFromCell() {
    Cell cellToDelete = board.getBoardCells().get("B3");
    board.deleteFigureFromCell(cellToDelete);
    assertTrue(cellToDelete.isEmpty());
  }

  @Test
  void getAliveFigures() {
    Set<Cell> setOfAliveFigureCells = board.getAliveFigures(new Player("white"));
    assertEquals(12, setOfAliveFigureCells.size());
  }

  @Test
  void changeCellFigure() {
    Figure newFigure = new King(new Player(""));
    Cell cellToChange = board.getBoardCells().get("B3");
    board.changeCellFigure(cellToChange, newFigure);
    assertEquals(newFigure, cellToChange.getFigure());
  }
}
