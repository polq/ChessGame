package chess.player;

import chess.player.ChessPlayer;

public class BlackPlayer extends ChessPlayer {

  public BlackPlayer(int defaultStep) {
    super(defaultStep);
  }

  public BlackPlayer() {}

  @Override
  public String getQueenIcon() {
    return "\u265B"; // ♛
  }

  @Override
  public String toString() {
    return "Black Player";
  }
}
