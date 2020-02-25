package boardgame.items.boardcell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardFactoryTest {

  BoardFactory boardFactory;

  @BeforeEach
  void init() {
    boardFactory = new ChessBoardFactory();
  }

  @Test
  void testGetInitialBoard() {
    Map<String, Cell> boardMap = boardFactory.generateBoardCells();
    long figuresNumber = boardMap.values().stream().filter(cell -> !cell.isEmpty()).count();

    assertEquals(64, boardMap.size());
    assertEquals(32, figuresNumber);
  }

  @Disabled
  @Test
  void testGetInitialPlayerQueue() {
    // Queue<Player> players = boardFactory.generatePlayerQueue();

    // assertEquals(2, players.size());
  }
}
