package chess.items.board;

import chess.rules.GameRule;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    System.out.println(board);
  }
}
