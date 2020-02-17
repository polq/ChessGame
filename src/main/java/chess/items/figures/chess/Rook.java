package chess.items.figures.chess;

import chess.behavior.Castlable;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class Rook extends Figure implements Castlable {

  public Rook(Player chessOwner, String chessIcon) {
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
