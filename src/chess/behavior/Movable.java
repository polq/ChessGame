package chess.behavior;

import chess.items.board.Cell;

public interface Movable {

  boolean move(Cell start, Cell destination);

  boolean beat(Cell start, Cell destination);
}
