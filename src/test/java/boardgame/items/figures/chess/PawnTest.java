package boardgame.items.figures.chess;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CellBuilder;
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
    blackPawn = new Pawn(new Player("black",-1));
    whitePawn = new Pawn(new Player("white",1));
  }

  @Test
  void testWhiteMove() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 5).getResultCell();
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 4).getResultCell();
    assertTrue(whitePawn.canMove(startCell, endCell));
    // Already moved, can move by 2 cells only once
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new CellBuilder('e', 3).getResultCell();
    endCell = new CellBuilder('e', 2).getResultCell();
    assertFalse(whitePawn.canMove(startCell, endCell));

    startCell = new CellBuilder('a', 3).getResultCell();
    endCell = new CellBuilder('b', 3).getResultCell();
    assertFalse(whitePawn.canMove(startCell, endCell));
  }

  @Test
  void testBlackMove() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 5).getResultCell();
    assertFalse(blackPawn.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('E', 4).getResultCell();
    assertFalse(blackPawn.canMove(startCell, endCell));
    assertFalse(blackPawn.canMove(startCell, endCell));

    startCell = new CellBuilder('e', 3).getResultCell();
    endCell = new CellBuilder('e', 2).getResultCell();
    assertTrue(blackPawn.canMove(startCell, endCell));

    startCell = new CellBuilder('a', 3).getResultCell();
    endCell = new CellBuilder('b', 3).getResultCell();
    assertFalse(blackPawn.canMove(startCell, endCell));
  }

  @Test
  void testExceptions() {
    assertThrows(NullPointerException.class, () -> whitePawn.canMove(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    assertThrows(IllegalArgumentException.class, () -> blackPawn.canMove(startCell, startCell));
  }

  @Test
  void testWhiteBeat() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('d', 3).getResultCell();
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('f', 3).getResultCell();
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('e', 3).getResultCell();
    assertFalse(whitePawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('d', 4).getResultCell();
    assertFalse(whitePawn.canBeat(startCell, endCell));
  }

  @Test
  void testBlackBeat() {
    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('d', 1).getResultCell();
    assertTrue(blackPawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('f', 1).getResultCell();
    assertTrue(blackPawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 2).getResultCell();
    endCell = new CellBuilder('e', 1).getResultCell();
    assertFalse(blackPawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('E', 3).getResultCell();
    endCell = new CellBuilder('d', 1).getResultCell();
    assertFalse(blackPawn.canBeat(startCell, endCell));
  }

  @Test
  void testBeat() {
    startCell = new CellBuilder('a', 1).getResultCell();
    endCell = new CellBuilder('b', 2).getResultCell();
    assertTrue(whitePawn.canBeat(startCell, endCell));

    startCell = new CellBuilder('b', 2).getResultCell();
    endCell = new CellBuilder('b', 4).getResultCell();
    assertFalse(whitePawn.canMove(startCell, endCell));
  }
}
