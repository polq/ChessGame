package chess.items.figures.chess;

import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class Bishop extends Figure {

  public Bishop(Player chessOwner, String chessIcon) {
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
