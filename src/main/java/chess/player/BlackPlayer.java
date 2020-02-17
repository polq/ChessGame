package chess.player;

public class BlackPlayer extends Player {

  public BlackPlayer(int defaultStep) {
    super(defaultStep);
  }

  public BlackPlayer() {

  }

  @Override
  public String getNewFigureIcon() {
    return "\u265B"; // ♛
  }

  @Override
  public String toString() {
    return "Black Player";
  }
}
