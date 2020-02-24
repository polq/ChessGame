package boardgame.player;

public class WhitePlayer extends Player {

  public WhitePlayer(int defaultStep) {
    super(defaultStep);
  }

  public WhitePlayer() {}

  @Override
  public String toString() {
    return "White Player";
  }
}
