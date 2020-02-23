package boardgame.game;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.items.boardcell.Board;
import boardgame.items.boardcell.BoardFactory;
import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
import boardgame.items.figures.checkers.Checker;
import boardgame.player.Player;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.artifact.repository.metadata.Snapshot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckersGameAITest {

  CheckersGameAI checkersGameAI;

  @BeforeEach
  void init() {
    checkersGameAI = new CheckersGameAI();
  }

  @Test
  void getGameStatus() {
    String gameStatus = checkersGameAI.getGameStatus();
    assertEquals("It's WHITE turn", gameStatus);
  }

  @Test
  void isActive() {
    assertTrue(checkersGameAI.isActive());
  }

  @Test
  void executeCommandNull() {
    assertThrows(NullPointerException.class, () -> checkersGameAI.executeCommand(null));
  }

  @Test
  void executeCommandNullFirstCell() {
    String coordinates = "H9 A2";
    assertThrows(IllegalArgumentException.class, () -> checkersGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandNullOtherCell() {
    String coordinates = "B3 B2 I9";
    assertThrows(IllegalArgumentException.class, () -> checkersGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandNoFigureInFirstCell() {
    String coordinates = "A1 B2 A7";
    assertThrows(IllegalArgumentException.class, () -> checkersGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandFirstFigureAnotherOwner() {
    String coordinates = "A8 B2 A7";
    assertThrows(IllegalArgumentException.class, () -> checkersGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandOtherCellsAreNotEmpty() {
    String coordinates = "B3 C8 A7";
    assertThrows(IllegalArgumentException.class, () -> checkersGameAI.executeCommand(coordinates));
  }

  @Test
  void executeCommandMove() {
    String coordinates = "B3 A4";
    checkersGameAI.executeCommand(coordinates);
    assertNotNull(checkersGameAI.getGameBoard().getBoardCells().get("A4").getFigure());
  }

  @Test
  void executeCommandBeat() {
    String coordinatesFirstMove = "B3 A4";
    String coordinatesSecondMove = "C6 B5";
    String coordinatesBeat = "A4 C6";
    checkersGameAI.executeCommand(coordinatesFirstMove);
    checkersGameAI.switchPlayer();
    checkersGameAI.executeCommand(coordinatesSecondMove);
    checkersGameAI.switchPlayer();
    checkersGameAI.executeCommand(coordinatesBeat);

    assertNotNull(checkersGameAI.getGameBoard().getBoardCells().get("C6").getFigure());
    assertTrue(checkersGameAI.getGameBoard().getBoardCells().get("B5").isEmpty());
  }

  @Test
  void testFindFiguresBetweenToBeat() {
    assertTrue(checkersGameAI.isOnlyOneFigureBetweenToBeat(new CellBuilder('B', 3).getResultCell(),
        new CellBuilder('F', 7).getResultCell()));
  }

}