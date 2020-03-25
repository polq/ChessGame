package org.buzevych.core.boardgame.items.figures.chess;

import org.buzevych.core.boardgame.items.board.Cell;
import org.buzevych.core.boardgame.items.figures.Figure;
import org.buzevych.core.boardgame.player.Player;

public class Bishop extends Figure {

  public Bishop(Player figureOwner) {
    super(figureOwner);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    return (Math.abs(start.getPositionLetter() - destination.getPositionLetter())
        == Math.abs(start.getPositionNumber() - destination.getPositionNumber()));
  }

  @Override
  public String toString() {
    return "BISHOP";
  }
}
