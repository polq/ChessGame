package boardgame.items.figures.checkers;

import boardgame.behavior.Changeable;
import boardgame.items.cell.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Checker extends Figure implements Changeable {


  public Checker(Player figureOwner, String chessIcon) {
    super(figureOwner, chessIcon);
  }

  public Checker(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    int defaultStep = getFigureOwner().getDefaultStep();
    return (destination.getPositionNumber() - start.getPositionNumber() == defaultStep
        && Math.abs(start.getPositionLetter() - destination.getPositionLetter()) == 1);
  }

  @Override
  public boolean beat(Cell start, Cell destination) {
    return (Math.abs(start.getPositionNumber() - destination.getPositionNumber()) == 2
        && Math.abs(start.getPositionLetter() - destination.getPositionLetter()) == 2);
  }

  @Override
  public String getNewFigureIcon() {
    return getNewIcon();
  }
}
