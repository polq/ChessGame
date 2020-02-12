package chess.items.chesspieces.bishop;

import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

public class Bishop extends ChessFigure {

  public Bishop(ChessPlayer chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    return (Math.abs(start.getPositionLetter() - destination.getPositionLetter())
        == Math.abs(start.getPositionNumber() - destination.getPositionNumber()));
  }

  @Override
  public String toString() {
    return super.toString() + " Bishop";
  }
}
