package boardgame.game;

import boardgame.items.board.ChessBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameTest {

  Game chessGame;

  @BeforeEach
  void init() {
    chessGame = new ChessGame();
    chessGame.startNewGame(new ChessBoardFactory());
  }

  @Test
  void startNewGame() {
    chessGame.startNewGame(new ChessBoardFactory());
    assertNotNull(chessGame);
  }

  @Test
  void startNewGameNullRule() {
    assertThrows(NullPointerException.class, () -> chessGame.startNewGame(null));
  }

  @Test
  void playNull() {
    assertThrows(NullPointerException.class, () -> chessGame.play(null));
  }

  @Test
  void playWithOutStartingGame() {
    chessGame = new ChessGame();
    assertThrows(NullPointerException.class, () -> chessGame.play("E2 E4"));
  }

  @Test
  void playInvalidCoordinates() {
    assertThrows(IllegalArgumentException.class, () -> chessGame.play("A1 I9"));
  }

  @Test
  void play() {
    assertDoesNotThrow(() -> chessGame.play("E2 E4"));
  }

  @Test
  void testToString() {
    assertEquals(chessGame.getGameBoard().toString(), chessGame.toString());
  }
}
