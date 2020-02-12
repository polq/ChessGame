package chess.gamestate;

import chess.behavior.Jumpable;
import chess.behavior.Queenable;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.items.chesspieces.king.King;
import chess.items.chesspieces.queen.Queen;
import chess.player.ChessPlayer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class GameState {

  private Board gameBoard;

  public GameState(Board board) {
    this.gameBoard = board;
  }

  public Board getGameBoard() {
    return gameBoard;
  }

  public abstract GameState switchGameState();

  public ChessPlayer getCurrentTurnPlayer() {
    return gameBoard.getPlayersQueue().peek();
  }

  public ChessPlayer switchPlayer() {
    ChessPlayer previousPlayer = gameBoard.getPlayersQueue().remove();
    gameBoard.getPlayersQueue().add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  // Method checks if player is playing with his own chess pieces
  boolean checkOwner(ChessFigure figure) {
    return figure.getChessOwner().getClass().equals(getCurrentTurnPlayer().getClass());
  }

  public abstract void executeCommand(String fromCoordinate, String toCoordinate);

  void executeMove(Cell fromCell, Cell toCell) {
    ChessFigure figure = fromCell.getFigure();

    // Check if figure can move from the given cell to the destination one and there are no
    // obstacles on their path
    if (!figure.move(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }
    if (figure instanceof King && isUnderCheck(figure.getChessOwner(), toCell)) {
      throw new IllegalArgumentException("Invalid input, King cannot move under check");
    }

    // Modifying cell from where chess piece has moved
    fromCell.figureMovedFromThisCell();
    // Modifying cell to where chess has moved
    toCell.figureMovedToThisCell(figure);

    // if figure that can become a Queen moves to a queenable cell
    if (figure instanceof Queenable && toCell instanceof Queenable) {
      executeBecomeQueen(figure, toCell);
    }
  }

  void executeBecomeQueen(ChessFigure previousFigure, Cell toCell) {
    ChessPlayer player = previousFigure.getChessOwner();
    toCell.setFigure(new Queen(player, player.getQueenIcon()));
  }

  void executeBeat(Cell fromCell, Cell toCell) {
    ChessFigure figure = fromCell.getFigure();
    if (!figure.beat(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot beat from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    if (figure instanceof King && isUnderCheck(figure.getChessOwner(), toCell)) {
      throw new IllegalArgumentException("Invalid input, King cannot move under check");
    }

    fromCell.figureMovedFromThisCell();
    toCell.figureMovedToThisCell(figure);

    if (figure instanceof Queenable && toCell instanceof Queenable) {
      executeBecomeQueen(figure, toCell);
    }
  }

  void executeCastle(Cell fromCell, Cell toCell) {
  }

  // find if cell is under check
  boolean isUnderCheck(ChessPlayer player, Cell kingCell) {
    List<Cell> enemyfigures =
        gameBoard.getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getChessOwner().getClass().equals(player.getClass()))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .collect(Collectors.toList());

    return !enemyfigures.isEmpty();
  }

  // find king by player color
  Cell findKing(ChessPlayer player) {
    return getGameBoard().getBoardCells().entrySet().stream()
        .filter(entry -> !entry.getValue().isEmpty())
        .filter(
            entry ->
                entry.getValue().getFigure().getChessOwner().getClass().equals(player.getClass()))
        .filter(entry -> entry.getValue().getFigure() instanceof King)
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No king on the board, game is already over"));
  }

  boolean isUnderCheckMate(ChessPlayer player, Cell kingCell) {
    List<Cell> potentialCellList =
        gameBoard.getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(entry -> !entry.equals(kingCell))
            .filter(Cell::isEmpty)
            .filter(entry -> kingCell.getFigure().move(kingCell, entry))
            .collect(Collectors.toList());

    Optional<Cell> potentialUncheckedCells =
        potentialCellList.stream().filter(cell -> !isUnderCheck(player, cell)).findAny();

    return potentialUncheckedCells.isEmpty();
  }

  // checks if moving path from one cell to another is not occupied
  boolean isPathClear(Cell startPoint, Cell endPoint, ChessFigure figure) {

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
            gameBoard.getBoardCells().get("" + (char) i + startPoint.getPositionNumber());
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else if (startPoint.getPositionLetter()
        == endPoint.getPositionLetter()) { // letters identical - vertical movement
      int min = Integer.min(startPoint.getPositionNumber(), endPoint.getPositionNumber());
      int abs = Math.abs(startPoint.getPositionNumber() - endPoint.getPositionNumber());

      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath = gameBoard.getBoardCells().get("" + startPoint.getPositionLetter() + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else {
      int numberAbs = Math.abs(startPoint.getPositionNumber() - endPoint.getPositionNumber());
      int letterAbs = Math.abs(startPoint.getPositionLetter() - endPoint.getPositionLetter());

      /*chess pieces can only move vertically, horizontally and by diagonal (where difference between letters and
      numbers must be identical */
      if (numberAbs != letterAbs) {
        return false;
      }
      int numberMin = Integer.min(startPoint.getPositionNumber(), endPoint.getPositionNumber());
      int letterMin = Integer.min(startPoint.getPositionLetter(), endPoint.getPositionLetter());

      for (int i = numberMin + 1, j = letterMin + 1; i < numberMin + numberAbs; i++, j++) {
        Cell cellOnPath = gameBoard.getBoardCells().get("" + (char) j + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
