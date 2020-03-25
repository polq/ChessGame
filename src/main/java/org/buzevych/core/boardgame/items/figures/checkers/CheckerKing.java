package org.buzevych.core.boardgame.items.figures.checkers;

import org.buzevych.core.boardgame.items.board.Cell;
import org.buzevych.core.boardgame.items.figures.Figure;
import org.buzevych.core.boardgame.player.Player;

public class CheckerKing extends Figure {

  public CheckerKing(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());
    return letterDifference == numberDifference;
  }

  @Override
  public String toString() {
    return "CHECKERKING";
  }
}
