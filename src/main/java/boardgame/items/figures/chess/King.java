package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class King extends Figure {

  public King(Player figureOwner) {
    super(figureOwner);
    this.setCastlable(true);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());

    return letterDifference <= 1 && numberDifference <= 1;
  }

  @Override
  public String toString() {
    return "KING";
  }
}
