package chess.player.playertypes;

import chess.player.ChessPlayer;

public class WhitePlayer extends ChessPlayer {

  public WhitePlayer(int defaultStep) {
    super(defaultStep);
  }

    public WhitePlayer() {

    }

    @Override
  public String toString() {
    return "White Player";
  }
}
