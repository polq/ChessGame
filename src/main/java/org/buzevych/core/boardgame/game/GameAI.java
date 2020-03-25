package org.buzevych.core.boardgame.game;

import org.buzevych.core.boardgame.items.board.Board;
import org.buzevych.core.boardgame.items.figures.Figure;
import org.buzevych.core.boardgame.player.Player;

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
 * <p>* Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public abstract class GameAI {

  static final String gameRuleDelimiters = "[- \\\\./|]";
  Board gameBoard;
  LinkedList<Player> playerQueue;

  /**
   * Method that is used to retrieve name of the game
   *
   * @return {@link String} representing games name
   */
  public abstract String getGameName();

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

  /**
   * Method that is used to change player's turn
   *
   * @return new {@link Player} that become current one
   */
  Player switchPlayer() {
    Player previousPlayer = playerQueue.remove();
    playerQueue.add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  /**
   * Method that is used to generate standard {@link Player's} queue when one is not explicitly
   * specified.
   *
   * @return LinkedList of standard black and white {@link Player}
   */
  static LinkedList<Player> generateStandardPlayerQueue() {
    LinkedList<Player> playersQueue = new LinkedList<>();
    playersQueue.add(new Player("white"));
    playersQueue.add(new Player("black"));
    return playersQueue;
  }

  /**
   * Method that is used to check if figure belongs to the current turn player
   *
   * @param figure that is to be checked
   * @return true - in case current player owns the figure, false - in case does not.
   */
  boolean checkFigureOwner(Figure figure) {
    return figure.getFigureOwner().equals(getCurrentTurnPlayer());
  }

  public Queue<Player> getPlayerQueue() {
    return playerQueue;
  }

  Player getCurrentTurnPlayer() {
    return playerQueue.peek();
  }
}
