package items.board;

import rules.GameRule;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Board {

  private Map<String,Cell> boardCells;
  private GameRule rules;

  public Board(GameRule rules) {
      this.rules = rules;
      initBoard();
  }

  private void initBoard(){
      this.boardCells = rules.getInitialBoardState();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int i = rules.getBoardWeight(); i >= 1; i--) {
      for (int j = 'A' + rules.getBoardHeight()-1; j >= 'A'; j--) {
          String cellKey = "" +i + (char) j;
          builder.append(boardCells.get(cellKey));
          builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
