package org.buzevych.boardgame.items.figures.checkers;

import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.items.figures.Figure;
import org.buzevych.boardgame.player.Player;

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
