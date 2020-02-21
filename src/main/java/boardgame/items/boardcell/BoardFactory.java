package boardgame.items.boardcell;

import boardgame.player.Player;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class BoardFactory {

  public static final char initialBoardWeight = 'A';
  public static final int initialBoardHeight = 1;

  abstract int getBoardWeight();

  abstract int getBoardHeight();

  public Board createBoard(){
    Board board = new Board();
    board.setPlayersQueue(this.generatePlayerQueue());
    board.setBoardCells(this.generateBoardCells());
    board.setBoardHeight(this.getBoardHeight());
    board.setBoardWeight(this.getBoardWeight());
    board.setFigureIcons(this.generateFigureIcons());
    return board;
  }


  abstract Map<String, String> generateFigureIcons();

  /**
   * Creates initial {@link boardgame.items.boardcell.Board} state including position of all {@link
   * boardgame.items.figures.Figure} on the Board for the defined game rule
   *
   * @return {@link Map} with {@link String} key representing coordinate on the {@link
   *     boardgame.items.boardcell.Board} and {@link Cell} Value representing all corresponding
   *     properties of the coordinate
   */
  abstract Map<String, Cell> generateBoardCells();

  /**
   * Method that creates {@link Player} that will be participating in the game. {@link Player} turns
   * will be defined by sequence of adding them to the queue. By default there is two player's but
   * this method can be overridden in the concrete class to make any amount of {@link Player's}
   *
   * @return {@link Queue} of containing {@link Player} that will take part in the game, while first
   *     player in the queue is current turn player
   */
  Queue<Player> generatePlayerQueue(){
    Queue<Player> playersQueue = new LinkedList<>();
    playersQueue.add(new Player("white"));
    playersQueue.add(new Player("black"));
    return playersQueue;
  }
}
