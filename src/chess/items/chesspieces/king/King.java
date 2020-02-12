package chess.items.chesspieces.king;

import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

public class King extends ChessFigure {

  public King(ChessPlayer chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());
    return (letterDifference <= 1 && numberDifference <= 1);
  }

  @Override
  public String toString() {
    return super.toString() + " King";
  }
}
