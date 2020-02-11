package chess.items.chesspieces;

import chess.behavior.Movable;
import chess.items.board.Cell;
import chess.player.ChessPlayer;

public abstract class ChessFigure implements Movable {

  ChessPlayer chessOwner;
  String chessIcon;

  public ChessFigure(ChessPlayer chessOwner, String chessIcon) {
    this.chessOwner = chessOwner;
    this.chessIcon = chessIcon;
  }

  public String getChessIcon() {
    return this.chessIcon;
  }

  public ChessPlayer getChessOwner() {
    return chessOwner;
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    if (start == null || destination == null) {
      throw new NullPointerException("Invalid null arguments");
    }

    if (start.equals(destination)) {
      throw new IllegalArgumentException("Method arguments cannot be equal");
    }
    return false;
  }

  @Override
  public boolean beat(Cell start, Cell destination) {
    return this.move(start, destination);
  }
}
