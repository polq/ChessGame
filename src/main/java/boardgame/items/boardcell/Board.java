package boardgame.items.boardcell;

import boardgame.items.figures.Figure;
import boardgame.player.Player;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * {@link Board} class represents game board object, which is being manipulated by {@link
 * boardgame.game.GameAI}. Board has fields that hold it's physical borders as well as all {@link
 * Cell} objects on it.
 *
 * <p>{@link Board} has methods to get Cells by input String coordinates, methods to move figures
 * and to get all alive figures on board.
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class Board {

  private Map<String, Cell> boardCells;
  private Map<String, String> figureIcons;
  private int boardWeight;
  private int boardHeight;

  Board() {}

  /**
   * Method is used to validate if coordinates are present on the board.
   *
   * @param inputCoordinates {@link String} array representing coordinates to be validated
   * @return {@link List} of Cells located on the input coordinates.
   * @throws IllegalArgumentException in case any of input coordinates is not present on the board.
   */
  public List<Cell> getCellList(String[] inputCoordinates) {
    List<Cell> cellList = new ArrayList<>();
    for (String coordinate : inputCoordinates) {
      Cell newCell = boardCells.get(coordinate);
      if (newCell == null) {
        throw new IllegalArgumentException(
            "Invalid move coordinates, input coordinates should be in range of board size: "
                + getBoardHeight()
                + "x"
                + getBoardWeight());
      }
      cellList.add(newCell);
    }
    return cellList;
  }

  /**
   * Method is used to move figures depending on the Cell's in parameters. In case the is no figure
   * on the second Cell, simple move is executed, in case there is enemy figure on the second Cell -
   * it will be beaten. Otherwise, figures will be swapped.
   *
   * @param fromCell representing from where Figure should move.
   * @param toCell representing to where the Figure would move.
   */
  public void moveFigures(Cell fromCell, Cell toCell) {
    Figure figureToBeMoved = fromCell.getFigure();
    Figure figureToBeBeaten = toCell.getFigure();
    if (figureToBeBeaten == null) {
      toCell.figureMovedToThisCell(figureToBeMoved);
      fromCell.figureMovedFromThisCell();
    } else if (figureToBeMoved.getFigureOwner().equals(figureToBeBeaten.getFigureOwner())) {
      toCell.figureMovedToThisCell(figureToBeMoved);
      fromCell.figureMovedToThisCell(figureToBeBeaten);
      figureToBeBeaten.setMoved(true);
    } else {
      toCell.figureMovedToThisCell(figureToBeMoved);
      fromCell.figureMovedFromThisCell();
    }
    figureToBeMoved.setMoved(true);
  }

  /**
   * Methods to delete a {@link Figure} from the Cell
   *
   * @param cellToDelete {@link Cell} from where figure is to be deleted
   * @return deleted {@link Figure}
   */
  public Figure deleteFigureFromCell(Cell cellToDelete) {
    Figure deletedFigure = cellToDelete.getFigure();
    cellToDelete.figureMovedFromThisCell();
    return deletedFigure;
  }

  /**
   * Method is used to get all alive figures on the board per player
   *
   * @param player representing figures owner
   * @return set of all cells with figures that belong to the specified owner
   */
  public Set<Cell> getAliveFigures(Player player) {
    return getBoardCells().values().stream()
        .filter(Predicate.not(Cell::isEmpty))
        .filter(cell -> cell.getFigure().getFigureOwner().equals(player))
        .collect(Collectors.toSet());
  }

  /**
   * Method sets a new Figure on the Cell
   *
   * @param cellWhereToChange Cell where new Figure would be set
   * @param newFigure new Figure that should be set
   */
  public void changeCellFigure(Cell cellWhereToChange, Figure newFigure) {
    cellWhereToChange.figureMovedToThisCell(newFigure);
  }

  public Map<String, String> getFigureIcons() {
    return figureIcons;
  }

  void setFigureIcons(Map<String, String> figureIcons) {
    this.figureIcons = figureIcons;
  }

  public int getBoardWeight() {
    return boardWeight;
  }

  void setBoardWeight(int boardWeight) {
    this.boardWeight = boardWeight;
  }

  public int getBoardHeight() {
    return boardHeight;
  }

  void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }

  public Map<String, Cell> getBoardCells() {
    return boardCells;
  }

  void setBoardCells(Map<String, Cell> boardCells) {
    this.boardCells = boardCells;
  }
}
