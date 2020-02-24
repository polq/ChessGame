package boardgame.items.figures.chess;

import boardgame.behavior.Castlable;
import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

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
