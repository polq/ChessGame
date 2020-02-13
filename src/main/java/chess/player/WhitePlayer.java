package chess.player;

import chess.player.ChessPlayer;

public class WhitePlayer extends ChessPlayer {

  public WhitePlayer(int defaultStep) {
    super(defaultStep);
  }

  public WhitePlayer() {}

  @Override
  public String getQueenIcon() {
    return "\u2655";
  }

  @Override
  public String toString() {
    return "White Player";
  }
}
