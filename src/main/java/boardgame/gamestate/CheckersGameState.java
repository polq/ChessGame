package boardgame.gamestate;

import boardgame.behavior.Changeable;
import boardgame.exception.GameOverException;
import boardgame.items.board.Board;
import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.items.figures.checkers.CheckerKing;
import boardgame.player.Player;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Concrete class that defines main public methods to execute command, received from the {@link
 * boardgame.game.Game} class, and to get game status message.
 *
 * <p>Additionally class has private-package util methods that are used to determinate if the
 * commands passed to the execute method are valid and to perform the corresponding changes on the
 * {@link Board}
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class CheckersGameState extends GameState {

  private Cell tempFromCell;

  /**
   * Creates new {@link GameState} class with the board specified in the param.
   *
   * @param board represents initial state of the board when {@link boardgame.game.Game} has started
   */
  public CheckersGameState(Board board) {
    super(board);
  }

  /**
   * Method returns brief game status information identifying current player's turn
   *
   * @return {@link String} containing current player's turn unless the game is already over
   * @throws GameOverException when current player has no figures left
   */
  @Override
  public String getGameStatus() {
    if (getAliveFigures(getCurrentTurnPlayer()).size() < 1) {
      throw new GameOverException("Game over " + getCurrentTurnPlayer() + " has lost");
    } else {
      return "It's " + getCurrentTurnPlayer() + " turn";
    }
  }

  /**
   * Main method that takes input Coordinates array and executes corresponding commands depending on
   * the coordinates type. First array item should represent the {@link Figure} {@link Cell} and
   * other {@link String} coordinates should represent empty {@link Cell} where figure should be
   * moved.
   *
   * <p>In case initial checks are satisfied, the method invokes {@code move} or {@code beat}
   * methods which might throw {@link IllegalArgumentException} in case those moves cannot be
   * performed according to the defined rules.
   *
   * @param inputCoordinates {@link String} array representing {@link Cell} coordinates first
   *     coordinate representing figure to move and other - {@link Cell} where it should be moved.
   * @throws NullPointerException in case any of the {@link Cell} coordinate specified in the param
   *     does not exist on the {@link Board}
   * @throws IllegalArgumentException if first {@link Cell} in the param does not contain a figure
   *     or figure belongs to another player or if other {@link Cell} coordinates are not empty
   */
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
    if (toCells.stream().anyMatch(cell -> cell == null)) {
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

  @Override
  void executeBeat(Cell fromCell, Cell toCell) {
    executeBeat(fromCell, Arrays.asList(new Cell[] {toCell}));
  }

  private void executeBeat(Cell fromCell, List<Cell> toCell) {
    Figure figureToBeat = fromCell.getFigure();
    Cell tempFromCell = fromCell;
    Cell tempToCell;
    for (int i = 0; i < toCell.size(); i++) {
      tempToCell = toCell.get(i);
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

    if (getPotentialCellsToBeat(lastCellInChain).size() > 1) {
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

  private List<Cell> findFiguresBetweenToBeat(Cell fromCell, Cell toCell) {
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

  private void becomeCheckerKing(Cell cell) {
    if (cell.getFigure() instanceof Changeable && cell.isChangeable()) {
      Player player = cell.getFigure().getChessOwner();
      cell.setFigure(new CheckerKing(player, ((Changeable) cell.getFigure()).getNewFigureIcon()));
    }
  }

  // finds if there any current player figure that can beat someone
  private boolean isAnyFigureUnderBeat() {
    return getAliveFigures(getCurrentTurnPlayer()).stream()
        .filter(cell -> canFigureBeat(cell))
        .findAny()
        .isPresent();
  }

  private List<Cell> getPotentialCellsToBeat(Cell figureCell) {
    return getGameBoard().getBoardCells().entrySet().stream()
        .map(Map.Entry::getValue)
        .filter(cell -> figureCell.getFigure().beat(figureCell, cell))
        .filter(Cell::isEmpty)
        .filter(cell -> isOnlyOneFigureBetweenToBeat(figureCell, cell))
        .filter(
            cell ->
                !findFiguresBetweenToBeat(figureCell, cell)
                    .get(0)
                    .getFigure()
                    .getChessOwner()
                    .equals(figureCell.getFigure().getChessOwner()))
        .collect(Collectors.toList());
  }

  private boolean canFigureBeat(Cell figureCell) {
    return !getPotentialCellsToBeat(figureCell).isEmpty();
  }
}
