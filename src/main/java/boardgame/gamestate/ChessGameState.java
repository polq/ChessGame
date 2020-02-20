package boardgame.gamestate;

import boardgame.behavior.Castlable;
import boardgame.behavior.Changeable;
import boardgame.behavior.Jumpable;
import boardgame.exception.GameOverException;
import boardgame.items.board.Board;
import boardgame.items.cell.Cell;
import boardgame.items.cell.CellBuilder;
import boardgame.items.figures.Figure;
import boardgame.items.figures.checkers.CheckerKing;
import boardgame.items.figures.chess.King;
import boardgame.items.figures.chess.Queen;
import boardgame.items.figures.chess.Rook;
import boardgame.player.Player;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
public class ChessGameState extends GameState {

  /**
   * Creates new {@link GameState} class with the board specified in the param.
   *
   * @param board represents initial state of the board when {@link boardgame.game.Game} has started
   */
  public ChessGameState(Board board) {
    super(board);
  }

  /**
   * Method returns brief game status information identifying current player's turn and special game
   * situation (check) if necessary.
   *
   * @return {@link String} containing current player's turn unless the game is already over
   * @throws GameOverException if there is checkmate situation on the board identifying the lose of
   *     the specified player or when there is a stalemate situation identifying draw
   */
  @Override
  public String getGameStatus() {
    String result = "It's " + getCurrentTurnPlayer() + " turn.";
    if (isUnderCheck(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
        throw new GameOverException("GameOver " + getCurrentTurnPlayer() + " has lost");
      } else {
        return result + getCurrentTurnPlayer() + " is under check";
      }
    } else {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))
          && getAliveFigures(getCurrentTurnPlayer()).size() <= 1) {
        throw new GameOverException("GameOver. It's a draw");
      } else {
        return result;
      }
    }
  }

  /**
   * Main method that takes input Coordinates array and executes corresponding commands depending on
   * the coordinates type. First array item should represent the {@link Figure} {@link Cell} and
   * other {@link String} coordinate should represent another {@link Cell} where first figure should
   * move.
   *
   * <p>In case initial checks are satisfied, the method invokes {@code move}, {@code beat} {@code
   * castle} methods which might throw {@link IllegalArgumentException} in case those moves cannot
   * be performed according to the defined rules.
   *
   * @param inputCoordinates {@link String} array representing {@link Cell} coordinates first
   *     coordinate representing figure to move and second {@link Cell} where this figure should
   *     move/beat figure located on this cell
   * @throws NullPointerException in case any of the {@link Cell} coordinate specified in the param
   *     does not exist on the {@link Board}
   * @throws IllegalArgumentException if first {@link Cell} in the param does not contain a figure
   *     or figure belongs to another player or if other {@link Cell} coordinates are not empty
   */
  @Override
  public void executeCommand(String[] inputCoordinates) {
    String fromCoordinate = inputCoordinates[0];
    String toCoordinate = inputCoordinates[1];
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    Cell fromCell = gameBoardCells.get(fromCoordinate);
    Cell toCell = gameBoardCells.get(toCoordinate);

    if (fromCell == null || toCell == null) {
      throw new NullPointerException("Invalid input arguments");
    }

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
  void executeMove(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    if (!isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    moveFigure(fromCell, toCell);

    if (isUnderCheck(figure.getFigureOwner(), findKing(figure.getFigureOwner()))) {
      toCell.figureMovedFromThisCell();
      fromCell.figureMovedToThisCell(figure);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
    figure.setMoved(true);
    becomeQueen(figure, toCell);
  }

  @Override
  void executeBeat(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    Figure figureToBeat = toCell.getFigure();
    if (!figure.beat(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot beat from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    // Modifying cell from where chess piece has moved
    fromCell.figureMovedFromThisCell();
    // Modifying cell to where chess has moved
    toCell.figureMovedToThisCell(figure);

    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(figure.getFigureOwner(), findKing(figure.getFigureOwner()))) {
      toCell.figureMovedToThisCell(figureToBeat);
      fromCell.figureMovedToThisCell(figure);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }

    figure.setMoved(true);
    becomeQueen(figure, toCell);
  }

  private void executeCastle(Cell fromCell, Cell toCell) {
    if (fromCell.getFigure() == null || toCell.getFigure() == null) {
      throw new NullPointerException("Null arguments while castling");
    }

    if (!(fromCell.getFigure() instanceof Castlable)
        || !(toCell.getFigure() instanceof Castlable)) {
      throw new IllegalArgumentException("You cannot beat your own figures");
    }

    Cell kingCell;
    Cell rookCell;

    if (fromCell.getFigure() instanceof Rook && !(toCell.getFigure() instanceof Rook)) {
      rookCell = fromCell;
      kingCell = toCell;
    } else if (fromCell.getFigure() instanceof King) {
      kingCell = fromCell;
      rookCell = toCell;
    } else {
      throw new IllegalArgumentException("You cannot castle two rooks");
    }

    King king = (King) kingCell.getFigure();
    Rook rook = (Rook) rookCell.getFigure();

    if (!rook.move(fromCell, toCell)) {
      throw new IllegalArgumentException("Castling figures should be on the same line");
    }

    if (!isPathClear(fromCell, toCell, king)) {
      throw new IllegalArgumentException(
          "You can only castle when there is no obstacles in the path");
    }

    if (!king.castle()) {
      throw new IllegalArgumentException("You cannot castle a king that has moved");
    }

    kingCell.figureMovedToThisCell(rook);
    rookCell.figureMovedToThisCell(king);

    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(king.getFigureOwner(), findKing(king.getFigureOwner()))) {
      kingCell.figureMovedToThisCell(king);
      rookCell.figureMovedToThisCell(rook);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
  }

  private void becomeQueen(Figure previousFigure, Cell toCell) {
    if (previousFigure instanceof Changeable && toCell.isChangeable()) {
      Player player = previousFigure.getFigureOwner();
      toCell.figureMovedToThisCell(new Queen(player));
    }
  }

  private boolean isUnderCheck(Player player, Cell kingCell) {
    List<Cell> enemyfigures =
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getFigureOwner().equals(player))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .collect(Collectors.toList());

    return !enemyfigures.isEmpty();
  }

  private boolean isUnderCheckMate(Player player, Cell kingCell) {
    // First check if figures that check our king can be eliminated
    List<Cell> enemyFiguresUnderCheck =
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getFigureOwner().equals(player))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .filter(cell -> isUnderCheck(cell.getFigure().getFigureOwner(), cell))
            .collect(Collectors.toList());

    if (!enemyFiguresUnderCheck.isEmpty()) {
      return false;
    }

    // Then check if there any empty cells where king can move
    List<Cell> potentialEmptyCellList =
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(entry -> !entry.equals(kingCell))
            .filter(Cell::isEmpty)
            .filter(entry -> kingCell.getFigure().move(kingCell, entry))
            .collect(Collectors.toList());

    Optional<Cell> potentialUncheckedCells =
        potentialEmptyCellList.stream().filter(cell -> !isUnderCheck(player, cell)).findAny();

    return potentialUncheckedCells.isEmpty();
  }

  private Cell findKing(Player player) {
    return getGameBoard().getBoardCells().entrySet().stream()
        .filter(entry -> !entry.getValue().isEmpty())
        .filter(entry -> entry.getValue().getFigure().getFigureOwner().equals(player))
        .filter(entry -> entry.getValue().getFigure() instanceof King)
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No king on the board, game is already over"));
  }

  private boolean isPathClear(Cell startPoint, Cell endPoint, Figure figure) {

    if (startPoint == null || endPoint == null || figure == null) {
      throw new NullPointerException();
    }

    if (startPoint.equals(endPoint)) {
      throw new IllegalArgumentException("Start and End Points must be unique");
    }

    // Jumpable figures does not care about obstacles on their path
    if (figure instanceof Jumpable) {
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
    } else if (startPoint.getPositionLetter()
        == endPoint.getPositionLetter()) { // letters identical - vertical movement
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

      /*chess pieces can only move vertically, horizontally and by diagonal (where difference between letters and
      numbers must be identical */
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
