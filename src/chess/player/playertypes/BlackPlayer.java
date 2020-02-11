package chess.player.playertypes;

import chess.player.ChessPlayer;

public class BlackPlayer extends ChessPlayer {

    public BlackPlayer(int defaultStep) {
    super(defaultStep);
  }

    public BlackPlayer() {

    }

    @Override
  public String toString() {
    return "Black Player";
  }
}
