package boardgame.game;

import boardgame.gamestate.GameState;
import boardgame.items.board.Board;
import boardgame.rules.GameRule;

/**
 * The abstract {@code Game} class represents main class to work with the API The class {@code Game}
 * includes methods to start a new Game with the defined {@link GameRule} and to play a Game by
 * passing a {@link String} command.
 */
public abstract class Game {

  GameRule rule;
  Board gameBoard;
  GameState gameState;

  public Game() {}

  Board getGameBoard() {
    return gameBoard;
  }

  /**
   * Takes {@link String} line as an input command, checks it against validity and returns {@link
   * String} representation of current {@link Board} situation and corresponding message.
   *
   * @param inputCommand {@link String} argument representing {@link Board} coordinates in the
   *     format : 'Letter''Number' separated by either of the following delimiters [ -\./|] in case
   *     the figure should move several times in one turn, move coordinates should be specified in
   *     the sequential order
   * @return {@link String} representation of {@link Board} and some {@link String} status message
   * @throws NullPointerException in case the method was invoked before starting the game
   * @throws NullPointerException in case param in null
   * @throws IllegalArgumentException if param does not match the specified game board size
   * @throws boardgame.exception.GameOverException if game has ended
   */
  public String play(String inputCommand) {

    if (this.rule == null) {
      throw new NullPointerException(
          "In order to play a game, please start a new game by calling startNewGame(GameRule rule) function");
    }
    if (inputCommand == null) {
      throw new NullPointerException("InputCommand command cannot be null");
    }

    if (!checkValidInput(inputCommand)) {
      throw new IllegalArgumentException(
          "Invalid move coordinates, input coordinates should be in range of "
              + "board size separated by either of the following delimiters: ' ', '-', '/', '.', '|'");
    }

    inputCommand = inputCommand.toUpperCase();
    String[] inputCoordinates = inputCommand.split(GameRule.gameRuleDelimiters);

    this.gameState.executeCommand(inputCoordinates);
    this.gameState.switchPlayer();
    String gameStatus = this.gameState.getGameStatus();

    return this.gameState.toString() + "\n" + gameStatus;
  }

  /**
   * Starts the new game with the specified {@link GameRule}
   *
   * @param rule represents the {@link GameRule} with which the game should be started
   */
  public abstract void startNewGame(GameRule rule);

  abstract boolean checkValidInput(String inputCommand);

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
