package chess.gamestate;

import chess.behavior.Jumpable;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

import java.util.regex.Matcher;
import java.util.stream.IntStream;

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
        == startPoint.getPositionLetter()) { // letters identical - vertical movement
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
