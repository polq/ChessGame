package chess.player;

import chess.behavior.Queenable;

public abstract class ChessPlayer implements Queenable {

  private int defaultStep;

  public ChessPlayer() {}

  public ChessPlayer(int defaultStep) {
    this.defaultStep = defaultStep;
  }

  public int getDefaultStep() {
    return defaultStep;
  }
}
