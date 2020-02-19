package boardgame.player;

public abstract class Player {

  private int defaultStep;

  public Player() {}

  public Player(int defaultStep) {
    this.defaultStep = defaultStep;
  }

  public int getDefaultStep() {
    return defaultStep;
  }
}
