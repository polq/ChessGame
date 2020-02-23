package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessGameAITest {

  ChessGameAI chessGameAI;

  @BeforeEach
  void init() {
    chessGameAI = new ChessGameAI();
  }

  @Test
  void getGameStatusActive() {
    assertEquals("It's " + chessGameAI.getCurrentTurnPlayer() + " turn.",
        chessGameAI.getGameStatus());
  }

  @Test
  void executeCommandNull() {
    assertThrows(NullPointerException.class, () -> chessGameAI.executeCommand(null));
  }

  @Test
  void executeCommandNoCellsOnBoard() {
    String noFirstCoordinate = "A9 B2";
    String noSecondCoordinate = "A2 I2";

    assertThrows(IllegalArgumentException.class, () -> chessGameAI.executeCommand(noFirstCoordinate));
    assertThrows(IllegalArgumentException.class, () -> chessGameAI.executeCommand(noSecondCoordinate));
  }

  @Test
  void executeCommandNoFigureOnFirstCell() {
    String noFigureOnFirstCell = "A3 B2";
    assertThrows(
        IllegalArgumentException.class, () -> chessGameAI.executeCommand(noFigureOnFirstCell));
  }

  @Test
  void executeCommandFigureOnTheFirstCellBelongsToAnotherPlayer() {
    String coordinates = "A8 B2";
    assertThrows(IllegalArgumentException.class, () -> chessGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandMove() {
    String coordinates = "E2 E4";
    chessGameAI.executeCommand(coordinates);
    assertFalse(chessGameAI.getGameBoard().getBoardCells().get("E4").isEmpty());
  }

  @Test
  void executeCommandBeat() {
    String firsMoveCoordinate = "E2 E4";
    String secondMoveCoordinate = "D7 D5";
    String beatCoordinate = "E4 D5";

    chessGameAI.executeCommand(firsMoveCoordinate);
    chessGameAI.switchPlayer();
    chessGameAI.executeCommand(secondMoveCoordinate);
    chessGameAI.switchPlayer();
    chessGameAI.executeCommand(beatCoordinate);

    assertNotNull(chessGameAI.getGameBoard().getBoardCells().get("D5").getFigure());
  }

  @Test
  void executeCommandCastle() {
    String castleCoordinates = "A1 D1";
    assertThrows(IllegalArgumentException.class,
        () -> chessGameAI.executeCommand(castleCoordinates));
  }

}