package chess.items.chesspieces.rook;

import chess.behavior.Castlable;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

public class Rook extends ChessFigure implements Castlable {

  public Rook(ChessPlayer chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    return (start.getPositionNumber() == destination.getPositionNumber()
        || start.getPositionLetter() == destination.getPositionLetter());
  }

  @Override
  public String toString() {
    return super.toString() + " Rook";
  }
}
