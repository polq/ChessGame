package chess.game;

import chess.gamestate.GameState;
import chess.items.board.Board;
import chess.rules.GameRule;

public abstract class Game {

  GameRule rule;
  Board gameBoard;
  GameState gameState;

  public Game() {}

  Board getGameBoard() {
    return gameBoard;
  }

  GameState getGameState() {
    return gameState;
  }

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
              + "board size separated by either of the following delimiters: "
              + GameRule.gameRuleDelimiters);
    }

    inputCommand = inputCommand.toUpperCase();
    String[] inputCoordinates = inputCommand.split(GameRule.gameRuleDelimiters);

    this.gameState.executeCommand(inputCoordinates);
    this.gameState.switchPlayer();
    String gameStatus = this.gameState.getGameStatus();

    return this.gameState.toString() + "\n" + gameStatus;
  }

  public abstract void startNewGame(GameRule rule);

  abstract boolean checkValidInput(String inputCommand);

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
