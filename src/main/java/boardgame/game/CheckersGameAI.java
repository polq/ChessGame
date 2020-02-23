package boardgame.game;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CheckersBoardFactory;
import boardgame.items.figures.Figure;
import boardgame.items.figures.checkers.CheckerKing;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Concrete class that defines main public methods to execute command, received from the {@link
 * GameAI} class, and to get game status message.
 *
 * <p>Additionally class has private-package util methods that are used to determinate if the
 * commands passed to the execute method are valid and to perform the corresponding changes on the
 * {@link boardgame.items.boardcell.Board}
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in
 * this class will cause a {@link NullPointerException} to be thrown.
 */
public class CheckersGameAI extends GameAI {

  public CheckersGameAI() {
    this.gameBoard = new CheckersBoardFactory().createBoard();
    this.playerQueue = generatePlayerQueue();
  }

  /**
   * Method returns brief game status information identifying current player's turn
   *
   * @return {@link String} containing current player's turn unless the game is already over
   */
  @Override
  String getGameStatus() {
    String resultGameStatus;
    if (gameBoard.getAliveFigures(getCurrentTurnPlayer()).size() < 1) {
      resultGameStatus = "Game over " + getCurrentTurnPlayer() + " has lost";
    } else {
      resultGameStatus = "It's " + getCurrentTurnPlayer() + " turn";
    }
    return resultGameStatus;
  }

  /**
   * Method is used to check if game is still active or has been ended.
   *
   * @return true if the game is still active, false in case the game ended.
   */
  @Override
  boolean isActive() {
    return gameBoard.getAliveFigures(getCurrentTurnPlayer()).size() > 0;
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
   * @param inputCommand {@link String} representing {@link Cell} coordinates first coordinate
   *                     representing figure to move and other - {@link Cell} where it should be
   *                     moved.
   * @throws NullPointerException     in case any of the {@link Cell} coordinate specified in the
   *                                  param does not exist on the {@link boardgame.items.boardcell.Board}
   * @throws IllegalArgumentException if first {@link Cell} in the param does not contain a figure
   *                                  or figure belongs to another player or if other {@link Cell}
   *                                  coordinates are not empty
   */
  @Override
  void executeCommand(String inputCommand) {
    String[] coordinates = spitInputIntoCoordinates(inputCommand);
    List<Cell> cellsList = gameBoard.getCellList(coordinates);
    validateCellsForGameRules(cellsList);
    Cell fromCell = cellsList.get(0);
    Cell toCell = cellsList.get(1);
    if (cellsList.size() == 2 && !isOnlyOneFigureBetweenToBeat(fromCell, toCell)) {
      executeMove(fromCell, toCell);
    } else {
      executeBeat(fromCell, cellsList.subList(1, cellsList.size()));
    }
  }

  private void executeMove(Cell fromCell, Cell toCell) {
    Figure figureToMove = fromCell.getFigure();
    if (isAnyFigureUnderBeat()) {
      throw new IllegalArgumentException(
          "You cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey()
              + " as there is at least one enemy figure under beat");
    }
    if (!figureToMove.canMove(fromCell, toCell)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figureToMove.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }
    gameBoard.moveFigures(fromCell, toCell);
    if (figureToMove.isChangeable() && toCell.isChangeable()) {
      gameBoard.changeCellFigure(toCell, new CheckerKing(figureToMove.getFigureOwner()));
    }
  }

  private void executeBeat(Cell fromCell, List<Cell> toCells) {
    Figure figureToBeat = fromCell.getFigure();
    Cell tempFromCell = fromCell;
    Cell tempToCell;
    for (Cell cell : toCells) {
      tempToCell = cell;
      if (!figureToBeat.canBeat(tempFromCell, tempToCell)) {
        throw new IllegalArgumentException(
            "Invalid arguments, figure on "
                + tempFromCell.getStringKey()
                + " cannot beat in the provided sequence");
      }
      tempFromCell = tempToCell;
    }
    Cell lastCellInChain = toCells.get(toCells.size() - 1);
    gameBoard.moveFigures(fromCell, lastCellInChain);

    if (getPotentialCellsToBeat(lastCellInChain).size() > 1) {
      gameBoard.moveFigures(lastCellInChain, fromCell);
      throw new IllegalArgumentException(
          "There is another figure in the chain that can be beaten, please insert type in full sequence of checkers to beat");
    } else {
      for (Cell tempCell : toCells) {
        Cell cellToDelete = findFiguresBetweenToBeat(fromCell, tempCell).get(0);
        gameBoard.deleteFigureFromCell(cellToDelete);
        fromCell = tempCell;
      }
    }
  }

  private String[] spitInputIntoCoordinates(String inputCommand) {
    if (inputCommand == null) {
      throw new NullPointerException("InputCommand command cannot be null");
    }
    String regex = "\\w\\d+";
    Pattern pattern =
        Pattern.compile(
            "(" + regex + ")+(" + GameAI.gameRuleDelimiters + regex + ")+",
            Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputCommand);
    if (!matcher.matches()) {
      throw new IllegalArgumentException(
          "Invalid move coordinates, input coordinates should be in range of "
              + "board size separated by either of the following delimiters: ' ', '-', '/', '.', '|'");
    }
    return inputCommand.split(GameAI.gameRuleDelimiters);
  }

  private void validateCellsForGameRules(List<Cell> cellList) {
    Cell fromCell = cellList.get(0);
    Figure fromFigure = fromCell.getFigure();
    if (fromFigure == null || !checkFigureOwner(fromFigure)) {
      throw new IllegalArgumentException("The first argument must represent current player figure");
    }

    if (cellList.stream().skip(1).anyMatch(cell -> cell.getFigure() != null)) {
      throw new IllegalArgumentException("Checker can only move to an empty cell");
    }
  }

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
        posNumber = posNumber + numberStep, posLetter += letterStep, count++) {
      String cellKey = "" + (char) posLetter + posNumber;
      Cell cellBetween = gameBoard.getBoardCells().get(cellKey);
      if (cellBetween != null && !cellBetween.isEmpty()) {
        listOfCellsBetween.add(cellBetween);
      }
    }
    return listOfCellsBetween;
  }

  private boolean isAnyFigureUnderBeat() {
    return gameBoard.getAliveFigures(getCurrentTurnPlayer()).stream()
        .anyMatch(cell -> !getPotentialCellsToBeat(cell).isEmpty());
  }

  private List<Cell> getPotentialCellsToBeat(Cell figureCell) {
    return getGameBoard().getBoardCells().values().stream()
        .filter(cell -> figureCell.getFigure().canBeat(figureCell, cell))
        .filter(Cell::isEmpty)
        .filter(cell -> isOnlyOneFigureBetweenToBeat(figureCell, cell))
        .filter(
            cell ->
                !findFiguresBetweenToBeat(figureCell, cell)
                    .get(0)
                    .getFigure()
                    .getFigureOwner()
                    .equals(figureCell.getFigure().getFigureOwner()))
        .collect(Collectors.toList());
  }
}
