package items.chesspieces.queen;

import items.chesspieces.ChessFigure;

public abstract class Queen extends ChessFigure {
  @Override
  public boolean move() {
    return false;
  }

  @Override
  public boolean beat() {
    return false;
  }
}
