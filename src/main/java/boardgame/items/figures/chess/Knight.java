package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Knight extends Figure {


  public Knight(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());

    return (letterDifference != 0
        && numberDifference != 0
        && (letterDifference + numberDifference == 3));
  }

  @Override
  public String toString() {
    return "KNIGHT";
  }
}
