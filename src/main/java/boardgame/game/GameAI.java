package boardgame.game;

import boardgame.items.boardcell.Board;
import boardgame.items.figures.Figure;
import boardgame.player.Player;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Abstract class that defines methods to main public methods to execute command, received from the
 * {@link GameStarter} class, to get game status message and to define if the game is still active.
 *
 * <p>Additionally class has private-package util methods that are common for all board games, such
 * as getting current players turn, getting all alive {@link Figure} on {@link Board} or checking if
 * current {@link Player} owns the passed {@link Figure}
 *
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in
 * this class will cause a {@link NullPointerException} to be thrown.
 */
public abstract class GameAI {

  static final String gameRuleDelimiters = "[- \\\\./|]";
  Board gameBoard;
  Queue<Player> playerQueue;

  /**
   * Method is used to check if game is still active or has been ended.
   *
   * @return true if the game is still active, false in case the game ended.
   */
  abstract boolean isActive();

  /**
   * Method is used to execute command passed by third party.
   *
   * @param inputString {@link String} representing {@link Board} coordinates
   */
  abstract void executeCommand(String inputString);

  /**
   * Is used to get useful information about in which state the game is right now
   *
   * @return {@link String} representation of the game status depending on the type of a game
   */
  abstract String getGameStatus();

  Board getGameBoard() {
    return gameBoard;
  }

  Player switchPlayer() {
    Player previousPlayer = playerQueue.remove();
    playerQueue.add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  Player getCurrentTurnPlayer() {
    return playerQueue.peek();
  }

  Queue<Player> generatePlayerQueue() {
    Queue<Player> playersQueue = new LinkedList<>();
    playersQueue.add(new Player("white"));
    playersQueue.add(new Player("black"));
    return playersQueue;
  }

  boolean checkFigureOwner(Figure figure) {
    return figure.getFigureOwner().equals(getCurrentTurnPlayer());
  }

}
