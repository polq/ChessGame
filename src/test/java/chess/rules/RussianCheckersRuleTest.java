package chess.rules;

import chess.items.board.Board;
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
  public void rule() {
    Board board = new Board(rule);
    System.out.println(board);
  }
}
