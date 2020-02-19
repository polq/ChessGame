package boardgame.items.figures.chess;

import boardgame.behavior.Castlable;
import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

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
