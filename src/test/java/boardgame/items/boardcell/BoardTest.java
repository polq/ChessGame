package boardgame.items.boardcell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  BoardFactory rule;
  Board board;

  @BeforeEach
  void init() {
    rule = new ChessBoardFactory();
    board = rule.createBoard();
  }

  @Test
  void testToString() {
    //System.out.println(board);
  }
}
