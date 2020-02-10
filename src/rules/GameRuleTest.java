package rules;

import items.board.Board;
import items.board.Cell;
import org.junit.jupiter.api.Test;
import rules.ruletypes.StandardChessRules;

import java.util.Map;
import java.util.Set;


class GameRuleTest {

  @Test
  void getInitialBoardState() {

    Board board = new Board(new StandardChessRules());

    System.out.println(board);
  }
}