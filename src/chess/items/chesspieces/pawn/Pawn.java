package chess.items.chesspieces.pawn;

import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

public class Pawn extends ChessFigure {

  public Pawn(ChessPlayer chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    //pawn can only move vertically, so letter must remain unchanged
    if(start.getPositionLetter() != start.getPositionNumber()){
      return false;
    }

    return true;
  }

  @Override
  public boolean beat(Cell start, Cell destination) {
    return super.beat(start, destination);
  }
}
