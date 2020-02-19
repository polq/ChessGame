package boardgame.items.board;

import boardgame.player.Player;
import boardgame.rules.GameRule;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

public class Board {

  private Map<String, Cell> boardCells;
  private Queue<Player> playersQueue;
  private final int BOARD_WEIGHT;
  private final int BOARD_HEIGHT;

  public Board(GameRule rules) {
    this.boardCells = rules.generateBoardCells();
    this.playersQueue = rules.generatePlayerQueue();
    this.BOARD_WEIGHT = rules.getBoardWeight();
    this.BOARD_HEIGHT = rules.getBoardHeight();
  }

  public Queue<Player> getPlayersQueue() {
    return playersQueue;
  }

  public Map<String, Cell> getBoardCells() {
    return boardCells;
  }
  public void setBoardCells(Map<String, Cell> boardCells) {
    this.boardCells = boardCells;
  }

  public void setPlayersQueue(Queue<Player> playersQueue) {
    this.playersQueue = playersQueue;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("  ");
    int firstLetter = GameRule.initialBoardWeight;
    int lastLetter = firstLetter + BOARD_WEIGHT;
    IntStream.range(firstLetter, lastLetter).forEach(i -> builder.append((char) i + " "));
    builder.append("\n");

    for (int i = BOARD_HEIGHT; i >= 1; i--) {
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
