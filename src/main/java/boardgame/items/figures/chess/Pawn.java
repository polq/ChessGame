package boardgame.items.figures.chess;

import boardgame.behavior.Changeable;
import boardgame.items.board.Cell;
import boardgame.items.figures.Figure;
import boardgame.player.Player;

public class Pawn extends Figure implements Changeable {

  public Pawn(Player chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);

    // pawn can only move vertically, so letter must remain unchanged
    if (start.getPositionLetter() != destination.getPositionLetter()) {
      return false;
    }

    // Default step value for White pawn is 1 (can move 1 cell up), for black -1 (can move 1 cell
    // down the board)
    int defaultStep = this.getChessOwner().getDefaultStep();
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
  public boolean beat(Cell start, Cell destination) {
    if (start == null || destination == null) {
      throw new NullPointerException("Invalid null arguments");
    }

    if (start.equals(destination)) {
      throw new IllegalArgumentException("Method arguments cannot be equal");
    }
    int defaultStep = this.getChessOwner().getDefaultStep();

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
    return super.toString() + " Pawn";
  }

  @Override
  public String getNewFigureIcon() {
    return getNewIcon();
  }
}
