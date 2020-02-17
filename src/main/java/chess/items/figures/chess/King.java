package chess.items.figures.chess;

import chess.behavior.Castlable;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class King extends Figure implements Castlable {

  public King(Player chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());

    return letterDifference <= 1 && numberDifference <= 1;
  }

  @Override
  public boolean castle() {
    return !isMoved();
  }

  @Override
  public String toString() {
    return super.toString() + " King";
  }
}
