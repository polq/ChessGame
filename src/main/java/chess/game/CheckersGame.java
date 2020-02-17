package chess.game;

import chess.gamestate.CheckersGameState;
import chess.items.board.Board;
import chess.rules.GameRule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckersGame extends Game {

  @Override
  public void startNewGame(GameRule rule) {
    this.rule = rule;
    this.gameBoard = new Board(rule);
    this.gameState = new CheckersGameState(getGameBoard());
  }

  // ([A-H][1-8])+([ -/][A-H][1-8])+
  @Override
  boolean checkValidInput(String inputCommand) {
    char fromChar = GameRule.initialBoardWeight;
    char toChar = (char) (fromChar + this.rule.getBoardWeight() - 1);
    int fromInt = GameRule.initialBoardHeight;
    int toInt = this.rule.getBoardHeight();
    String regex = String.format("[%c-%c][%d-%d]", fromChar, toChar, fromInt, toInt);
    Pattern pattern =
        Pattern.compile(
            regex + "+" + GameRule.gameRuleDelimiters + regex + "+", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(inputCommand);
    return matcher.matches();
  }
}
