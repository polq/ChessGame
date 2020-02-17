package chess.player;

import chess.behavior.Changeable;

public abstract class Player implements Changeable {

  private int defaultStep;

  public Player() {}

  public Player(int defaultStep) {
    this.defaultStep = defaultStep;
  }

  public int getDefaultStep() {
    return defaultStep;
  }
}
