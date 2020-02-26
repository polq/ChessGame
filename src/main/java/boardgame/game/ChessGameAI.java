package boardgame.game;

import boardgame.items.board.BoardFactory;
import boardgame.items.board.ChessBoardFactory;
import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.items.figures.chess.King;
import boardgame.items.figures.chess.Queen;
import boardgame.items.figures.chess.Rook;
import boardgame.player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Concrete class that defines main public methods to execute command, received from the {@link
 * GameStarter} class, and to get game status message.
 *
 * <p>Additionally class has private-package util methods that are used to determinate if the
 * commands passed to the execute method are valid and to perform the corresponding changes on the
 * {@link boardgame.items.board.Board}
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class ChessGameAI extends GameAI {

  /**
   * Creates new {@link GameAI} class with the specified chess board in the param. Additionally sets
   * player's queue
   */
  public ChessGameAI() {
    this(generateStandardPlayerQueue());
  }

  public ChessGameAI(LinkedList<Player> playersQueue) {
    this.gameBoard = BoardFactory.createBoard(this.getGameName(), playersQueue);
    this.playerQueue = playersQueue;
  }

  /**
   * Method is used to check if game is still active or has been ended.
   *
   * @return true if the game is still active, false in case the game ended.
   */
  @Override
  boolean isActive() {
    return (!isUnderCheck(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))
            || !isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer())))
        && (!isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))
            || gameBoard.getAliveFigures(getCurrentTurnPlayer()).size() > 1);
  }

  /**
   * Method returns brief game status information identifying current player's turn and special game
   * situation (check) if necessary.
   *
   * @return {@link String} containing current player's turn unless the game is already over
   */
  @Override
  String getGameStatus() {
    String result;
    if (isUnderCheck(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
        result = "GameOver " + getCurrentTurnPlayer() + " has lost";
      } else {
        result = getCurrentTurnPlayer() + " is under check";
      }
    } else {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))
          && gameBoard.getAliveFigures(getCurrentTurnPlayer()).size() <= 1) {
        result = "GameOver. It's a draw";
      } else {
        result = "It's " + getCurrentTurnPlayer() + " turn.";
      }
    }
    return result;
  }

  /**
   * Main method that takes input Coordinates and executes corresponding commands depending on the
   * coordinates type. First array item should represent the {@link Figure} {@link Cell} and other
   * {@link String} coordinate should represent another {@link Cell} where first figure should move.
   *
   * <p>In case initial checks are satisfied, the method invokes {@code move}, {@code beat} {@code
   * castle} methods which might throw {@link IllegalArgumentException} in case those moves cannot
   * be performed according to the defined rules.
   *
   * @param inputCommand {@link String} representing {@link Cell} coordinates first coordinate
   *     representing figure to move and second {@link Cell} where this figure should move/beat
   *     figure located on this cell
   * @throws NullPointerException in case any of the {@link Cell} coordinate specified in the param
   *     does not exist on the {@link boardgame.items.board.Board}
   * @throws IllegalArgumentException if first {@link Cell} in the param does not contain a figure
   *     or figure belongs to another player or if other {@link Cell} coordinates are not empty
   */
  @Override
  void executeCommand(String inputCommand) {
    String[] coordinates = spitInputIntoCoordinates(inputCommand);
    List<Cell> cellsList = gameBoard.getCellList(coordinates);
    Cell fromCell = cellsList.get(0);
    Cell toCell = cellsList.get(1);
    Figure fromFigure = fromCell.getFigure();
    if (fromFigure == null || !checkFigureOwner(fromFigure)) {
      throw new IllegalArgumentException("The first argument must represent current player figure");
    }

    if (toCell.isEmpty()) {
      executeMove(fromCell, toCell);
    } else {
      // if toCell contains player's own figure - try to execute Castle
      if (checkFigureOwner(toCell.getFigure())) {
        executeCastle(fromCell, toCell);
      } else {
        executeBeat(fromCell, toCell);
      }
    }
  }

  @Override
  public String getGameName() {
    return "chess";
  }

  private void executeBeat(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    if (!figure.canBeat(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot beat from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    gameBoard.moveFigures(fromCell, toCell);
    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(figure.getFigureOwner(), findKing(figure.getFigureOwner()))) {
      gameBoard.moveFigures(fromCell, toCell);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
    if (figure.isChangeable() && toCell.isChangeable()) {
      gameBoard.changeCellFigure(toCell, new Queen(figure.getFigureOwner()));
    }
  }

  private void executeCastle(Cell fromCell, Cell toCell) {
    if (!(fromCell.getFigure().isCastlable()) || !(toCell.getFigure().isCastlable())) {
      throw new IllegalArgumentException("You cannot beat your own figures");
    }

    Cell kingCell;
    Cell rookCell;

    if (fromCell.getFigure().toString().equals("ROOK")
        && !(toCell.getFigure().toString().equals("ROOK"))) {
      rookCell = fromCell;
      kingCell = toCell;
    } else if (fromCell.getFigure().toString().equals("KING")) {
      kingCell = fromCell;
      rookCell = toCell;
    } else {
      throw new IllegalArgumentException("You cannot castle two rooks");
    }

    King king = (King) kingCell.getFigure();
    Rook rook = (Rook) rookCell.getFigure();

    if (!rook.canMove(fromCell, toCell)) {
      throw new IllegalArgumentException("Castling figures should be on the same line");
    }

    if (!isPathClear(fromCell, toCell, king)) {
      throw new IllegalArgumentException(
          "You can only castle when there is no obstacles in the path");
    }

    if (king.isMoved()) {
      throw new IllegalArgumentException("You cannot castle a king that has moved");
    }

    gameBoard.moveFigures(kingCell, rookCell);
    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(king.getFigureOwner(), findKing(king.getFigureOwner()))) {
      gameBoard.moveFigures(rookCell, kingCell);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
  }

  private void executeMove(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    if (!isPathClear(fromCell, toCell, figure) || !figure.canMove(fromCell, toCell)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    gameBoard.moveFigures(fromCell, toCell);
    if (isUnderCheck(figure.getFigureOwner(), findKing(figure.getFigureOwner()))) {
      gameBoard.moveFigures(toCell, fromCell);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
    if (figure.isChangeable() && toCell.isChangeable()) {
      gameBoard.changeCellFigure(toCell, new Queen(figure.getFigureOwner()));
    }
  }

  private String[] spitInputIntoCoordinates(String inputCommand) {
    if (inputCommand == null) {
      throw new NullPointerException("InputCommand command cannot be null");
    }
    String regex = "\\w\\d+";
    Pattern pattern =
        Pattern.compile(regex + GameAI.gameRuleDelimiters + regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputCommand);
    if (!matcher.matches()) {
      throw new IllegalArgumentException(
          "Invalid move coordinates, input coordinates should be in range of "
              + "board size separated by either of the following delimiters: ' ', '-', '/', '.', '|'");
    }
    return inputCommand.split(GameAI.gameRuleDelimiters);
  }

  private boolean isUnderCheck(Player player, Cell kingCell) {
    List<Cell> enemyfigures =
        getGameBoard().getBoardCells().values().stream()
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getFigureOwner().equals(player))
            .filter(cell -> cell.getFigure().canBeat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .collect(Collectors.toList());

    return !enemyfigures.isEmpty();
  }

  private boolean isUnderCheckMate(Player player, Cell kingCell) {
    // First check if figures that check our king can be eliminated
    List<Cell> enemyFiguresUnderCheck =
        getGameBoard().getBoardCells().values().stream()
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getFigureOwner().equals(player))
            .filter(cell -> cell.getFigure().canBeat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .filter(cell -> isUnderCheck(cell.getFigure().getFigureOwner(), cell))
            .collect(Collectors.toList());

    if (!enemyFiguresUnderCheck.isEmpty()) {
      return false;
    }

    // Then check if there any empty cells where king can move
    List<Cell> potentialEmptyCellList =
        getGameBoard().getBoardCells().values().stream()
            .filter(entry -> !entry.equals(kingCell))
            .filter(Cell::isEmpty)
            .filter(entry -> kingCell.getFigure().canMove(kingCell, entry))
            .collect(Collectors.toList());

    Optional<Cell> potentialUncheckedCells =
        potentialEmptyCellList.stream().filter(cell -> !isUnderCheck(player, cell)).findAny();

    return potentialUncheckedCells.isEmpty();
  }

  private Cell findKing(Player player) {
    return getGameBoard().getBoardCells().values().stream()
        .filter(cell -> !cell.isEmpty())
        .filter(cell -> cell.getFigure().getFigureOwner().equals(player))
        .filter(cell -> cell.getFigure() instanceof King)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No king on the board, game is already over"));
  }

  private boolean isPathClear(Cell startPoint, Cell endPoint, Figure figure) {
    // Knight figure does not care about obstacles on their path
    if (figure.toString().equals("KNIGHT")) {
      return true;
    }

    // numbers identical - horizontal movement
    if (startPoint.getPositionNumber() == endPoint.getPositionNumber()) {
      int min = Integer.min(startPoint.getPositionLetter(), endPoint.getPositionLetter());
      int abs = Math.abs(startPoint.getPositionLetter() - endPoint.getPositionLetter());

      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath =
            getGameBoard().getBoardCells().get("" + (char) i + startPoint.getPositionNumber());
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else if (startPoint.getPositionLetter() == endPoint.getPositionLetter()) {
      // letters identical - vertical movement
      int min = Integer.min(startPoint.getPositionNumber(), endPoint.getPositionNumber());
      int abs = Math.abs(startPoint.getPositionNumber() - endPoint.getPositionNumber());
      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath =
            getGameBoard().getBoardCells().get("" + startPoint.getPositionLetter() + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else {
      int numberDifference = endPoint.getPositionNumber() - startPoint.getPositionNumber();
      int letterDifference = endPoint.getPositionLetter() - startPoint.getPositionLetter();
      if (Math.abs(numberDifference) != Math.abs(letterDifference)) {
        return false;
      }

      int numberStep = Math.abs(numberDifference) / numberDifference;
      int letterStep = Math.abs(letterDifference) / letterDifference;
      for (int i = startPoint.getPositionNumber() + numberStep,
              j = startPoint.getPositionLetter() + letterStep;
          i != endPoint.getPositionNumber();
          i += numberStep, j += letterStep) {
        Cell cellOnPath = getGameBoard().getBoardCells().get("" + (char) j + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    }

    return true;
  }
}
