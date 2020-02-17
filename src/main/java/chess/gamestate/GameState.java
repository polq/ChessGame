package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class GameState {

  private Board gameBoard;

  public GameState(Board board) {
    this.gameBoard = board;
  }

  public abstract String getGameStatus();

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
        .filter(cell -> cell.getFigure().getChessOwner().getClass().equals(player.getClass()))
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

  public Player switchPlayer() {
    Player previousPlayer = gameBoard.getPlayersQueue().remove();
    gameBoard.getPlayersQueue().add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  Player getCurrentTurnPlayer() {
    return gameBoard.getPlayersQueue().peek();
  }

  boolean checkFigureOwner(Figure figure) {
    return figure.getChessOwner().getClass().equals(getCurrentTurnPlayer().getClass());
  }

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
