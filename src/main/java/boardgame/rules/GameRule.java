package boardgame.rules;

import boardgame.items.board.Board;
import boardgame.items.board.Cell;
import boardgame.player.Player;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class GameRule {

  public static final char initialBoardWeight = 'A';
  public static final int initialBoardHeight = 1;
  public static final String gameRuleDelimiters = "[- \\\\./|]";

  public abstract int getBoardWeight();

  public abstract int getBoardHeight();

  public Board createBoard(){
     return new Board(this);
  }

  /**
   * Creates initial {@link boardgame.items.board.Board} state including position of all {@link
   * boardgame.items.figures.Figure} on the Board for the defined game rule
   *
   * @return {@link Map} with {@link String} key representing coordinate on the {@link
   *     boardgame.items.board.Board} and {@link Cell} Value representing all corresponding
   *     properties of the coordinate
   */
  public abstract Map<String, Cell> generateBoardCells();

  /**
   * Method that creates {@link Player} that will be participating in the game. {@link Player} turns
   * will be defined by sequence of adding them to the queue. By default there is two player's but
   * this method can be overridden in the concrete class to make any amount of {@link Player's}
   *
   * @return {@link Queue} of containing {@link Player} that will take part in the game, while first
   *     player in the queue is current turn player
   */
  public Queue<Player> generatePlayerQueue(){
    Queue<Player> playersQueue = new LinkedList<>();
    playersQueue.add(new Player("white"));
    playersQueue.add(new Player("black"));
    return playersQueue;
  }
}
