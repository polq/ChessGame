package boardgame.gamestate;

import boardgame.items.board.Board;
import boardgame.items.cell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Abstract class that defines methods to main public methods to execute command, received from the
 * {@link boardgame.game.Game} class, to get game status message and to switch current player's turn
 *
 * <p>Additionally class has private-package util methods that are common for all board games, such
 * as getting current players turn, getting all alive {@link Figure} on {@link Board} or checking if
 * current {@link Player} owns the passed {@link Figure}
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public abstract class GameState {

  private Board gameBoard;

  /**
   * Creates new {@link GameState} class with the board specified
   *
   * @param board represents initial state of the board when {@link boardgame.game.Game} has started
   */
  public GameState(Board board) {
    this.gameBoard = board;
  }

  /**
   * Method that is used to switch {@link Player} turn. Method removes the current player from top
   * of the {@link Board} player's queue and moves it to the end.
   *
   * @return {@link Player} object that represents that was second on the turn queue before invoking
   *     the method
   */
  public Player switchPlayer() {
    Player previousPlayer = gameBoard.getPlayersQueue().remove();
    gameBoard.getPlayersQueue().add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  /**
   * Is used to get useful information about in which state the game is right now
   *
   * @return {@link String} representation of the game status depending on the type of a game
   */
  public abstract String getGameStatus();

  /**
   * Method is used to execute command passed by third party.
   *
   * @param inputCoordinates {@link String} array representing {@link Board} coordinates
   */
  public abstract void executeCommand(String[] inputCoordinates);

  abstract void executeMove(Cell fromCell, Cell toCell);

  abstract void executeBeat(Cell fromCell, Cell toCell);

  Board getGameBoard() {
    return gameBoard;
  }

  Set<Cell> getAliveFigures(Player player) {
    return gameBoard.getBoardCells().entrySet().stream()
        .map(Map.Entry::getValue)
        .filter(Predicate.not(Cell::isEmpty))
        .filter(cell -> cell.getFigure().getFigureOwner().equals(player))
        .collect(Collectors.toSet());
  }

  void moveFigure(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();

    if (!figure.move(fromCell, toCell)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }
    // Modifying cell from where chess piece has moved
    fromCell.figureMovedFromThisCell();
    // Modifying cell to where chess has moved
    toCell.figureMovedToThisCell(figure);
  }

  Player getCurrentTurnPlayer() {
    return gameBoard.getPlayersQueue().peek();
  }

  boolean checkFigureOwner(Figure figure) {
    return figure.getFigureOwner().equals(getCurrentTurnPlayer());
  }

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
