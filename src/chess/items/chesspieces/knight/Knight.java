package chess.items.chesspieces.knight;

import chess.behavior.Jumpable;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

public class Knight extends ChessFigure implements Jumpable {

  public Knight(ChessPlayer chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }
}
