package boardgame.items.figures.chess;

import boardgame.behavior.Jumpable;
import boardgame.items.cell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Knight extends Figure implements Jumpable {


  public Knight(Player figureOwner, String chessIcon) {
    super(figureOwner, chessIcon);
  }

  public Knight(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());

    return (letterDifference != 0
        && numberDifference != 0
        && (letterDifference + numberDifference == 3));
  }

  @Override
  public String toString() {
    return super.toString() + " Knight";
  }
}
