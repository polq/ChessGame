package boardgame.items.figures.chess;

import boardgame.items.cell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Bishop extends Figure {


  public Bishop(Player figureOwner, String chessIcon) {
    super(figureOwner, chessIcon);
  }

  public Bishop(Player figureOwner) {
    super(figureOwner);
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
