package boardgame.rules;

import boardgame.items.board.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class StandardChessRuleTest {

  GameRule gameRule;

  @BeforeEach
  void init() {
    gameRule = new StandardChessRule();
  }

  @Test
  void testGetInitialBoard() {
    Map<String, Cell> boardMap = gameRule.generateBoardCells();
    long figuresNumber =
        boardMap.entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .count();

    assertEquals(64, boardMap.size());
    assertEquals(32, figuresNumber);
  }

  @Test
  void testGetInitialPlayerQueue() {
    Queue<Player> players = gameRule.generatePlayerQueue();

    assertEquals(2, players.size());
  }
}
