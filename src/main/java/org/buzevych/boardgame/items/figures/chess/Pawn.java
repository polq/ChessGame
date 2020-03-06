package org.buzevych.boardgame.items.figures.chess;

import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.items.figures.Figure;
import org.buzevych.boardgame.player.Player;

public class Pawn extends Figure {

  public Pawn(Player figureOwner) {
    super(figureOwner);
    this.setCanBeChanged(true);
  }

  @Override
  public boolean canMove(Cell start, Cell destination) {
    // pawn can only move vertically, so letter must remain unchanged
    if (start.getPositionLetter() != destination.getPositionLetter()) {
      return false;
    }

    // Default step value for White pawn is 1 (can move 1 cell up), for black -1 (can move 1 cell
    // down the board)
    int defaultStep = this.getFigureOwner().getDefaultStep();
    int currentStepValue = destination.getPositionNumber() - start.getPositionNumber();

    if (currentStepValue == defaultStep) {
      setMoved(true);
      return true;
    }
    // in case pawn has not moved yet, it can move up or down by 2 cell depending on it's color
    defaultStep = this.isMoved() ? defaultStep : defaultStep * 2;

    if (currentStepValue == defaultStep) {
      setMoved(true);
      return true;
    }

    return false;
  }

  @Override
  public boolean canBeat(Cell start, Cell destination) {
    int defaultStep = this.getFigureOwner().getDefaultStep();

    // pawn can only beat by diagonal for 1 step
    boolean result =
        (Math.abs(start.getPositionLetter() - destination.getPositionLetter()) == 1
            && destination.getPositionNumber() - start.getPositionNumber() == defaultStep);

    if (result) {
      setMoved(true);
    }
    return result;
  }

  @Override
  public String toString() {
    return "PAWN";
  }
}
