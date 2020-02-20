package boardgame.items.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CheckersBoardFactoryTest {

  BoardFactory rule;

  @BeforeEach
  public void init() {
    rule = new CheckersBoardFactory();
  }

  @Test
  void getInitialBoard() {
    Board board = rule.createBoard();
    assertEquals(64, board.getBoardCells().size());
  }
}
