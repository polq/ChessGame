package boardgame.game;

import boardgame.gamestate.CheckersGameState;
import boardgame.items.board.Board;
import boardgame.rules.GameRule;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Concrete {@link Game} class that has methods to start new checker game and to validate input
 * command (should be two(or more) coordinates in {@link Board} range separated by the required
 * delimiters
 */
public class CheckersGame extends Game {

  /**
   * Starts new Checker game with the specified {@link GameRule}
   *
   * @param rule represents the {@link GameRule} with which the game should be started
   * @throws NullPointerException in case the param in {@code null}
   */
  @Override
  public void startNewGame(GameRule rule) {
    if (rule == null) {
      throw new NullPointerException("GameRule cannot be empty");
    }
    this.rule = rule;
    this.gameBoard = new Board(rule);
    this.gameState = new CheckersGameState(getGameBoard());
  }

  /**
   * regex: ([s-c][n-e])+([[- \\./|]][s-c][n-e])+
   * Start char - s
   * End char - c
   * Start num - n;
   * End num - e
   */
  @Override
  boolean checkValidInput(String inputCommand) {
    char fromChar = GameRule.initialBoardWeight;
    char toChar = (char) (fromChar + this.rule.getBoardWeight() - 1);
    int fromInt = GameRule.initialBoardHeight;
    int toInt = this.rule.getBoardHeight();
    String regex = String.format("[%c-%c][%d-%d]", fromChar, toChar, fromInt, toInt);
    Pattern pattern =
        Pattern.compile(
            "(" + regex + ")+(" + GameRule.gameRuleDelimiters + regex + ")+",
            Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputCommand);
    return matcher.matches();
  }
}
