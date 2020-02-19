package boardgame.items.board;

import boardgame.player.Player;
import boardgame.rules.GameRule;

import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

public class Board {

  private Map<String, Cell> boardCells;
  private GameRule rules;
  private Queue<Player> playersQueue;

  public Queue<Player> getPlayersQueue() {
    return playersQueue;
  }

  public Map<String, Cell> getBoardCells() {
    return boardCells;
  }

  public Board(GameRule rules) {
    this.rules = rules;
    initBoard();
  }

  private void initBoard() {
    this.boardCells = rules.getInitialBoard();
    this.playersQueue = rules.getInitialPlayersQueue();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("  ");
    int firstLetter = GameRule.initialBoardWeight;
    int lastLetter = firstLetter + rules.getBoardWeight();
    IntStream.range(firstLetter, lastLetter).forEach(i -> builder.append((char) i + " "));
    builder.append("\n");

    for (int i = rules.getBoardHeight(); i >= 1; i--) {
      builder.append(i);
      builder.append(" ");
      for (int j = firstLetter; j < lastLetter; j++) {
        String cellKey = "" + (char) j + i;
        builder.append(boardCells.get(cellKey));
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
