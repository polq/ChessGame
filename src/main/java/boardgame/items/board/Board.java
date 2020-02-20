package boardgame.items.board;

import boardgame.items.cell.Cell;
import boardgame.player.Player;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

public class Board {

  private Map<String, Cell> boardCells;
  private Map<String, String> figureIcons;
  private Queue<Player> playersQueue;
  private int boardWeight;
  private int boardHeight;

  Board() {}

  public Map<String, String> getFigureIcons() {
    return figureIcons;
  }

  void setFigureIcons(Map<String, String> figureIcons) {
    this.figureIcons = figureIcons;
  }

  public int getBoardWeight() {
    return boardWeight;
  }

  void setBoardWeight(int boardWeight) {
    this.boardWeight = boardWeight;
  }

  public int getBoardHeight() {
    return boardHeight;
  }

  void setBoardHeight(int boardHeight) {
    this.boardHeight = boardHeight;
  }

  public Queue<Player> getPlayersQueue() {
    return playersQueue;
  }

  public Map<String, Cell> getBoardCells() {
    return boardCells;
  }

  void setBoardCells(Map<String, Cell> boardCells) {
    this.boardCells = boardCells;
  }

  void setPlayersQueue(Queue<Player> playersQueue) {
    this.playersQueue = playersQueue;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("  ");
    int firstLetter = BoardFactory.initialBoardWeight;
    int lastLetter = firstLetter + boardWeight;
    IntStream.range(firstLetter, lastLetter).forEach(i -> builder.append((char) i + " "));
    builder.append("\n");

    for (int i = boardHeight; i >= 1; i--) {
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
