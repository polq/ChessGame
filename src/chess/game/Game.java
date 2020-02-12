package chess.game;

import chess.gamestate.ActiveGameState;
import chess.gamestate.GameState;
import chess.items.board.Board;
import chess.rules.GameRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {

  private GameRule rule;
  private Board gameBoard;
  private GameState gameState;

  public Game() {}

  public GameState getGameState() {
    return gameState;
  }
  // main API method which takes input String and returns all info about board, game state and
  // modifications
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
    String fromCoordinate = inputCoordinates[0];
    String toCoordinate = inputCoordinates[1];

    this.gameState.executeCommand(fromCoordinate, toCoordinate);

    // if everything is OK, switch player's turn
    this.gameState.switchPlayer();
    // after command is executed, switch state (e.g if game just started or in check/mat states)
    this.gameState = gameState.switchGameState();

    return this.toString();
  }

  // start new method with a defined gameRule
  public void startNewGame(GameRule rule) {

    if (rule == null) {
      throw new NullPointerException("GameRule cannot be null");
    }

    this.rule = rule;
    this.gameBoard = new Board(rule);
    this.gameState = new ActiveGameState(gameBoard);
  }

  // check input coordinates for valid input
  boolean checkValidInput(String inputCommand) {
    char fromChar = GameRule.initialBoardWeight;
    char toChar = (char) (fromChar + this.rule.getBoardWeight() - 1);
    int fromInt = GameRule.initialBoardHeight;
    int toInt = this.rule.getBoardHeight();
    String regex = String.format("[%c-%c][%d-%d]", fromChar, toChar, fromInt, toInt);
    Pattern pattern =
        Pattern.compile(regex + GameRule.gameRuleDelimiters + regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputCommand);
    return matcher.matches();
  }

  @Override
  public String toString() {
    return this.gameState.toString();
  }
}
