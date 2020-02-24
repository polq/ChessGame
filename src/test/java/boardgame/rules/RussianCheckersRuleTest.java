package boardgame.rules;

import boardgame.items.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RussianCheckersRuleTest {

  GameRule rule;

  @BeforeEach
  public void init() {
    rule = new RussianCheckersRule();
  }

  @Test
  void getInitialBoard() {
    Board board = new Board(rule);
    assertEquals(64, board.getBoardCells().size());
  }
}
