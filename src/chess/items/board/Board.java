package chess.items.board;

import chess.items.board.Cell;
import chess.player.ChessPlayer;
import chess.rules.GameRule;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Board {

  private Map<String, Cell> boardCells;
  private GameRule rules;
  private Queue<ChessPlayer> playersQueue;

  public Queue<ChessPlayer> getPlayersQueue() {
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
    StringBuilder builder = new StringBuilder();
    builder.append("  A B C D E F G H\n");
    for (int i = rules.getBoardHeight(); i >= 1; i--) {
      builder.append(i);
      builder.append(" ");
      for (int j = GameRule.initialBoardWeight + rules.getBoardWeight() - 1;
          j >= GameRule.initialBoardWeight;
          j--) {
        String cellKey = "" + (char) j + i;
        builder.append(boardCells.get(cellKey));
        builder.append(" ");
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
