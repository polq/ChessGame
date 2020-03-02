package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Queen extends Figure {

  public Queen(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    // Queen can move in all directions - horizontally (numbers equal), vertically (letters equal)
    // or by diagonal (differences between letters and numbers are equal)
    return (start.getPositionNumber() == destination.getPositionNumber()
        || start.getPositionLetter() == destination.getPositionLetter()
        || (Math.abs(start.getPositionLetter() - destination.getPositionLetter())
            == Math.abs(start.getPositionNumber() - destination.getPositionNumber())));
  }

  @Override
  public String toString() {
    return "QUEEN";
  }
}
