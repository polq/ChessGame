package boardgame.items.figures.chess;

import boardgame.items.board.Cell;
import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

  Pawn blackPawn;
  Pawn whitePawn;
  Cell startCell;
  Cell endCell;

  @BeforeEach
  void init() {
    blackPawn = new Pawn(new Player("black", -1));
    whitePawn = new Pawn(new Player("white", 1));
  }

  @Test
  void testWhiteMoveTwoCells() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 4).build();
    assertTrue(whitePawn.canMove(startCell, endCell));
  }

  @Test
  void testWhiteMoveTwoCellsAfterMove() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('E', 4).build();
    whitePawn.canMove(startCell, endCell);
    assertFalse(whitePawn.canMove(startCell, endCell));
  }

  @Test
  void testBlackMoveTwoCells() {
    startCell = new Cell.Builder('E', 4).build();
    endCell = new Cell.Builder('E', 2).build();
    assertTrue(blackPawn.canMove(startCell, endCell));
  }

  @Test
  void testBlackMoveTwoCellsAfterMove() {
    startCell = new Cell.Builder('E', 4).build();
    endCell = new Cell.Builder('E', 2).build();
    blackPawn.canMove(startCell, endCell);
    assertFalse(blackPawn.canMove(startCell, endCell));
  }

  @Test
  void testWhiteMoveFail() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('F', 2).build();
    assertFalse(whitePawn.canMove(startCell, endCell));
  }

  @Test
  void testBlackMoveFail() {
    startCell = new Cell.Builder('A', 2).build();
    endCell = new Cell.Builder('B', 2).build();
    assertFalse(blackPawn.canMove(startCell, endCell));
  }

  @Test
  void testWhiteBeat() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('D', 3).build();
    assertTrue(whitePawn.canBeat(startCell, endCell));
  }

  @Test
  void testWhiteBeatFail() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('f', 3).build();
    assertFalse(whitePawn.canBeat(startCell, endCell));
  }

  @Test
  void testBlackBeat() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('D', 1).build();
    assertTrue(blackPawn.canBeat(startCell, endCell));
  }

  @Test
  void testBlackBeatFail() {
    startCell = new Cell.Builder('E', 2).build();
    endCell = new Cell.Builder('e', 1).build();
    assertFalse(blackPawn.canBeat(startCell, endCell));
  }
}
