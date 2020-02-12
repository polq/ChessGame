package chess.rules;

import chess.items.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImaginaryGameRuleTest {

  GameRule rule;
  Board board;

  @BeforeEach
  void init() {
    rule = new ImaginaryGameRule();
  }

  @Test
  void getInitBoard() {
    board = new Board(rule);
    assertEquals(16, board.getBoardCells().size());
  }

}
