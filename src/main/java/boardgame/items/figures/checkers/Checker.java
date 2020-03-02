package boardgame.items.figures.checkers;

import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Checker extends Figure {

  public Checker(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    int defaultStep = getFigureOwner().getDefaultStep();
    return (destination.getPositionNumber() - start.getPositionNumber() == defaultStep
        && Math.abs(start.getPositionLetter() - destination.getPositionLetter()) == 1);
  }

  @Override
  public boolean canBeat(Cell start, Cell destination) {
    return (Math.abs(start.getPositionNumber() - destination.getPositionNumber()) == 2
        && Math.abs(start.getPositionLetter() - destination.getPositionLetter()) == 2);
  }

  @Override
  public String toString() {
    return "CHECKER";
  }
}
