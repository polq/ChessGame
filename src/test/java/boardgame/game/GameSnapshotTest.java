package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameSnapshotTest {

  GameSnapshot gameSnapshot;

  @Test
  void getStringGameSnap() {
    gameSnapshot = new GameSnapshot.Builder().withBoard(new CheckersGameAI().gameBoard).build();
    assertDoesNotThrow(() -> gameSnapshot.getStringGameSnap());
  }

  @Test
  void getStringGameSnapError() {
    gameSnapshot = new GameSnapshot.Builder().withGameMessage("Error").build();
    assertEquals("Error", gameSnapshot.getStringGameSnap());
  }
}
