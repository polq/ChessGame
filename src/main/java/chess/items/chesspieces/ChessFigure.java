package chess.items.chesspieces;

import chess.behavior.Actionable;
import chess.items.board.Cell;
import chess.player.ChessPlayer;

public abstract class ChessFigure implements Actionable {

  ChessPlayer chessOwner;
  String chessIcon;
  boolean isMoved;

  public ChessFigure(ChessPlayer chessOwner, String chessIcon) {
    this.chessOwner = chessOwner;
    this.chessIcon = chessIcon;
    this.isMoved = false;
  }

  public String getChessIcon() {
    return this.chessIcon;
  }

  public ChessPlayer getChessOwner() {
    return chessOwner;
  }

  public boolean isMoved() {
    return isMoved;
  }

  public void setMoved(boolean moved) {
    isMoved = moved;
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

  @Override
  public String toString() {
    return this.getChessOwner().toString();
  }
}
