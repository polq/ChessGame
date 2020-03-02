package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.items.figures.Figure;
import boardgame.items.figures.chess.King;
import boardgame.player.Player;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameAITest {

  GameAI gameAI;

  @BeforeEach
  void init() {
    gameAI = new ChessGameAI();
  }

  @Test
  void switchPlayer() {
    Player player = gameAI.switchPlayer();
    assertEquals("BLACK", player.toString());
  }

  @Test
  void getCurrentTurnPlayer() {
    Player player = gameAI.getCurrentTurnPlayer();
    assertEquals("WHITE", player.toString());
  }

  @Test
  void generatePlayerQueue() {
    Queue<Player> playerQueue = GameAI.generateStandardPlayerQueue();
    assertEquals(2, playerQueue.size());
  }

  @Test
  void checkFigureOwner() {
    Player whitePlayer = gameAI.getCurrentTurnPlayer();
    Figure figure = new King(whitePlayer);
    assertTrue(gameAI.checkFigureOwner(figure));
  }
}
