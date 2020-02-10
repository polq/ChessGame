package rules;

import items.board.Cell;

import java.util.Map;
import java.util.Set;

public abstract class GameRule {

  public abstract int getPlayerCount();

  public abstract int getBoardWeight();

  public abstract int getBoardHeight();

  public abstract Map<String, Cell> getInitialBoardState();

}
