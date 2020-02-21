package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
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
    blackPawn = new Pawn(new Player("black",-1), "");
    whitePawn = new Pawn(new Player("white",1), "");
  }

  @Test
  void testWhiteMove() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 5);
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertTrue(whitePawn.canMove(startCell, endCell));
    // Already moved, can move by 2 cells only once
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new Cell('e', 3);
    endCell = new Cell('e', 2);
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new Cell('a', 3);
    endCell = new Cell('b', 3);
    assertFalse(whitePawn.canMove(startCell, endCell));
  }

  @Test
  void testBlackMove() {
    startCell = new Cell('E', 2);
    endCell = new Cell('E', 5);
    assertFalse(blackPawn.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('E', 4);
    assertFalse(blackPawn.canMove(startCell, endCell));
    assertFalse(blackPawn.canMove(startCell, endCell));

    startCell = new Cell('e', 3);
    endCell = new Cell('e', 2);
    assertTrue(blackPawn.canMove(startCell, endCell));

    startCell = new Cell('a', 3);
    endCell = new Cell('b', 3);
    assertFalse(blackPawn.canMove(startCell, endCell));
  }

  @Test
  void testExceptions() {
    assertThrows(NullPointerException.class, () -> whitePawn.canMove(startCell, endCell));

    startCell = new Cell('E', 2);
    assertThrows(IllegalArgumentException.class, () -> blackPawn.canMove(startCell, startCell));
  }

  @Test
  void testWhiteBeat() {
    startCell = new Cell('E', 2);
    endCell = new Cell('d', 3);
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('f', 3);
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('e', 3);
    assertFalse(whitePawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('d', 4);
    assertFalse(whitePawn.canBeat(startCell, endCell));
  }

  @Test
  void testBlackBeat() {
    startCell = new Cell('E', 2);
    endCell = new Cell('d', 1);
    assertTrue(blackPawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('f', 1);
    assertTrue(blackPawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 2);
    endCell = new Cell('e', 1);
    assertFalse(blackPawn.canBeat(startCell, endCell));

    startCell = new Cell('E', 3);
    endCell = new Cell('d', 1);
    assertFalse(blackPawn.canBeat(startCell, endCell));
  }

  @Test
  void testBeat() {
    startCell = new Cell('a', 1);
    endCell = new Cell('b', 2);
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new Cell('b', 2);
    endCell = new Cell('b', 4);
    assertFalse(whitePawn.canMove(startCell, endCell));
  }
}
