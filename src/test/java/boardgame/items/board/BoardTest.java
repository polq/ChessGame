package boardgame.items.board;

import boardgame.rules.GameRule;
import boardgame.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  GameRule rule;
  Board board;

  @BeforeEach
  void init() {
    rule = new StandardChessRule();
    board = new Board(rule);
  }

  @Test
  void testToString() {
    //System.out.println(board);
  }
}
