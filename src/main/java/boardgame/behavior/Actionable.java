package boardgame.behavior;

import boardgame.items.board.Cell;

public interface Actionable {

  boolean move(Cell start, Cell destination);

  boolean beat(Cell start, Cell destination);
}
