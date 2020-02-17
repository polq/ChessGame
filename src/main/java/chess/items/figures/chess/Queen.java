package chess.items.figures.chess;

import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.player.Player;

public class Queen extends Figure {

  public Queen(Player chessOwner, String chessIcon) {
    super(chessOwner, chessIcon);
  }

  @Override
  public boolean move(Cell start, Cell destination) {
    super.move(start, destination);
    // Queen can move in all directions - horizontally (numbers equal), vertically (letters equal)
    // or by diagonal (differences between letters and numbers are equal)
    return (start.getPositionNumber() == destination.getPositionNumber()
        || start.getPositionLetter() == destination.getPositionLetter()
        || (Math.abs(start.getPositionLetter() - destination.getPositionLetter())
            == Math.abs(start.getPositionNumber() - destination.getPositionNumber())));
  }

  @Override
  public String toString() {
    return super.toString() + " Queen";
  }
}
