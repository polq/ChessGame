package boardgame.items.boardcell;

import boardgame.items.figures.Figure;
import boardgame.player.Player;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Board {

  private Map<String, Cell> boardCells;
  private Map<String, String> figureIcons;
  private Queue<Player> playersQueue;
  private int boardWeight;
  private int boardHeight;

  Board() {}

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

  public Queue<Player> getPlayersQueue() {
    return playersQueue;
  }

  public Map<String, Cell> getBoardCells() {
    return boardCells;
  }

  void setBoardCells(Map<String, Cell> boardCells) {
    this.boardCells = boardCells;
  }

  void setPlayersQueue(Queue<Player> playersQueue) {
    this.playersQueue = playersQueue;
  }

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

  public Figure deleteFigureFromCell(Cell cellToDelete) {
    Figure deletedFigure = cellToDelete.getFigure();
    cellToDelete.figureMovedFromThisCell();
    return deletedFigure;
  }

  public Set<Cell> getAliveFigures(Player player) {
    return getBoardCells().values().stream()
        .filter(Predicate.not(Cell::isEmpty))
        .filter(cell -> cell.getFigure().getFigureOwner().equals(player))
        .collect(Collectors.toSet());
  }

  public void changeCellFigure(Cell cellWhereToChange, Figure newFigure) {
    cellWhereToChange.figureMovedToThisCell(newFigure);
  }
}
