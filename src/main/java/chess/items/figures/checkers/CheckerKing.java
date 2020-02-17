package chess.items.figures.checkers;

import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class CheckerKing extends Figure {

  public CheckerKing(Player chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    int letterDifference = Math.abs(start.getPositionLetter() - destination.getPositionLetter());
    int numberDifference = Math.abs(start.getPositionNumber() - destination.getPositionNumber());
    return letterDifference == numberDifference;
  }
}
