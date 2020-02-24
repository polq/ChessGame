package boardgame.player;

public class BlackPlayer extends Player {

  public BlackPlayer(int defaultStep) {
    super(defaultStep);
  }

  public BlackPlayer() {}

  @Override
  public String toString() {
    return "Black Player";
  }
}
