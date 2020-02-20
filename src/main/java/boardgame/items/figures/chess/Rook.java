package boardgame.items.figures.chess;

import boardgame.behavior.Castlable;
import boardgame.items.cell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Rook extends Figure implements Castlable {


  public Rook(Player figureOwner, String chessIcon) {
    super(figureOwner, chessIcon);
  }

  public Rook(Player figureOwner) {
    super(figureOwner);
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
