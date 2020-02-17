package chess.items.figures.checkers;

import chess.behavior.Changeable;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class Checker extends Figure implements Changeable {

  public Checker(Player chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    int defaultStep = getChessOwner().getDefaultStep();
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
