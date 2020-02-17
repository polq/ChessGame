package chess.gamestate;

import chess.behavior.Changeable;
import chess.exception.GameOverException;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.items.figures.checkers.CheckerKing;
import chess.player.Player;
import java.util.*;
import java.util.stream.Collectors;

public class CheckersGameState extends GameState {

  public CheckersGameState(Board board) {
    super(board);
  }

  @Override
  public String getGameStatus() {
    if (getAliveFigures(getCurrentTurnPlayer()).size() < 1) {
      throw new GameOverException("Game over " + getCurrentTurnPlayer() + " has lost");
    } else {
      return "It's " + getCurrentTurnPlayer() + " turn";
    }
  }

  @Override
  public void executeCommand(String[] inputCoordinates) {
    String fromCoordinate = inputCoordinates[0];
    Cell fromCell = getGameBoard().getBoardCells().get(fromCoordinate);
    List<Cell> toCells = new ArrayList<>();
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    for (int i = 1; i < inputCoordinates.length; i++) {
      toCells.add(gameBoardCells.get(inputCoordinates[i]));
    }

    if (fromCell == null) {
      throw new NullPointerException("The first cell is not present on the board");
    }
    if (toCells.stream().filter(cell -> cell == null).findAny().isPresent()) {
      throw new NullPointerException(
          "Invalid input arguments, some of the cells are not present on the board");
    }

    Figure fromFigure = fromCell.getFigure();
    if (fromFigure == null || !checkFigureOwner(fromFigure)) {
      throw new IllegalArgumentException("The first argument must represent current player figure");
    }

    if (toCells.stream().filter(cell -> cell.getFigure() != null).findAny().isPresent()) {
      throw new IllegalArgumentException("Checker can only move to an empty cell");
    }

    if (toCells.size() == 1) {
      if (isOnlyOneFigureBetweenToBeat(fromCell, toCells.get(0))) {
        executeBeat(fromCell, toCells.get(0));
      } else {
        executeMove(fromCell, toCells.get(0));
      }
    } else {
      executeBeat(fromCell, toCells);
    }
  }

  private void executeBeat(Cell fromCell, List<Cell> toCell) {
    Figure figureToBeat = fromCell.getFigure();
    Cell tempFromCell = fromCell;
    Cell tempToCell;
    for (int i = 0; i < toCell.size(); i++) {
      tempToCell = toCell.get(0);
      if (!figureToBeat.beat(tempFromCell, tempToCell)) {
        throw new IllegalArgumentException(
            "Invalid arguments, "
                + figureToBeat
                + " on "
                + tempFromCell.getStringKey()
                + " cannot beat in the provided sequence");
      }
      tempFromCell = tempToCell;
    }
    Cell lastCellInChain = toCell.get(toCell.size() - 1);
    fromCell.figureMovedFromThisCell();
    lastCellInChain.figureMovedToThisCell(figureToBeat);

    if (getFigureBeatList(lastCellInChain).size() > 1) {
      fromCell.figureMovedToThisCell(figureToBeat);
      lastCellInChain.figureMovedFromThisCell();
      throw new IllegalArgumentException(
          "There is another figure in the chain that can be beaten, please insert type in full sequence of checkers to beat");
    } else {
      for (int i = 0; i < toCell.size(); i++) {
        Cell tempCell = toCell.get(i);
        Cell cellToDelete = findFiguresBetweenToBeat(fromCell, tempCell).get(0);
        cellToDelete.figureMovedFromThisCell();
        fromCell = tempCell;
      }
    }
  }

  // returns true in case between cells there is only 1 figure to beat
  boolean isOnlyOneFigureBetweenToBeat(Cell fromCell, Cell toCell) {
    return findFiguresBetweenToBeat(fromCell, toCell).size() == 1;
  }

  List<Cell> findFiguresBetweenToBeat(Cell fromCell, Cell toCell) {
    List<Cell> listOfCellsBetween = new ArrayList<>();
    int letterDifference = toCell.getPositionLetter() - fromCell.getPositionLetter();
    int numberDifference = toCell.getPositionNumber() - fromCell.getPositionNumber();
    int letterStep = letterDifference / Math.abs(letterDifference);
    int numberStep = numberDifference / Math.abs(numberDifference);

    for (int posNumber = fromCell.getPositionNumber() + numberStep,
            posLetter = fromCell.getPositionLetter() + letterStep,
            count = 1;
        count < Math.abs(letterDifference);
        posNumber += numberStep, posLetter += letterStep, count++) {
      String cellKey = "" + (char) posLetter + posNumber;
      Cell cellBetween = getGameBoard().getBoardCells().get(cellKey);
      if (cellBetween != null && !cellBetween.isEmpty()) {
        listOfCellsBetween.add(cellBetween);
      }
    }

    return listOfCellsBetween;
  }

  @Override
  void executeMove(Cell fromCell, Cell toCell) {
    if (isAnyFigureUnderBeat()) {
      throw new IllegalArgumentException(
          "You cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey()
              + " as there is at least one enemy figure under beat");
    }
    moveFigure(fromCell, toCell);
    becomeCheckerKing(toCell);
  }

  void becomeCheckerKing(Cell cell) {
    if (cell.getFigure() instanceof Changeable && cell.isChangeable()) {
      Player player = cell.getFigure().getChessOwner();
      cell.setFigure(new CheckerKing(player, ((Changeable) cell.getFigure()).getNewFigureIcon()));
    }
  }

  @Override
  void executeBeat(Cell fromCell, Cell toCell) {
      executeBeat(fromCell, Arrays.asList(new Cell[] {toCell}));
  }

  // finds if there any current player figure that can beat someone
  boolean isAnyFigureUnderBeat() {
    return getAliveFigures(getCurrentTurnPlayer()).stream()
        .filter(cell -> canFigureBeat(cell))
        .findAny()
        .isPresent();
  }

  private List<Cell> getFigureBeatList(Cell figureCell) {
    List<Cell> enemyNeighbors =
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(
                cell ->
                    (Math.abs(cell.getPositionNumber() - figureCell.getPositionNumber()) <= 1)
                        && Math.abs(cell.getPositionLetter() - figureCell.getPositionLetter()) <= 1)
            .filter(cell -> !cell.isEmpty())
            .filter(
                cell ->
                    !cell.getFigure()
                        .getChessOwner()
                        .equals(figureCell.getFigure().getChessOwner()))
            .collect(Collectors.toList());

    if (enemyNeighbors.isEmpty()) {
      return Collections.EMPTY_LIST;
    }

    List<Cell> isEmptyNear =
        enemyNeighbors.stream()
            .filter(
                cell -> {
                  char cellLetter = cell.getPositionLetter();
                  int cellNumber = cell.getPositionNumber();
                  char letterDifference = (char) (cellLetter - figureCell.getPositionLetter());
                  int numberDifference = cellNumber - figureCell.getPositionNumber();

                  String cellKey =
                      "" + (char) (cellLetter + letterDifference) + (cellNumber + numberDifference);
                  Cell accrosCell;
                  if ((accrosCell = getGameBoard().getBoardCells().get(cellKey)) != null) {
                    return accrosCell.isEmpty();
                  } else {
                    return false;
                  }
                })
            .collect(Collectors.toList());

    return isEmptyNear;
  }

  private boolean canFigureBeat(Cell figureCell) {
    return !getFigureBeatList(figureCell).isEmpty();
  }
}
