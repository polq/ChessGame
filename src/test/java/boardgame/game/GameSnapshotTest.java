package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameSnapshotTest {

  GameSnapshot gameSnapshot;
  @Test
  void isActive() {
    gameSnapshot = GameSnapshot.buildJustStartedGameSnap(new CheckersGameAI());
    assertTrue(gameSnapshot.isActive());
  }

  @Test
  void getStringGameSnap() {
    gameSnapshot = GameSnapshot.buildJustStartedGameSnap(new CheckersGameAI());
    assertDoesNotThrow(() -> gameSnapshot.getStringGameSnap());
  }

  @Test
  void getStringGameSnapError(){
    gameSnapshot = GameSnapshot.buildErrorSnap("Error");
    assertEquals("Error", gameSnapshot.getStringGameSnap());
  }
}