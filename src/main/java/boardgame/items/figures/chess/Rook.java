package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Rook extends Figure{

  public Rook(Player figureOwner) {
    super(figureOwner);
    this.setCastlable(true);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    return (start.getPositionNumber() == destination.getPositionNumber()
        || start.getPositionLetter() == destination.getPositionLetter());
  }

  @Override
  public String toString() {
    return "ROOK";
  }
}
