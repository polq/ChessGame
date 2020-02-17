package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.rules.RussianCheckersRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGameStateTest {

  CheckersGameState gameState;

  @BeforeEach
  void init() {
    gameState = new CheckersGameState(new Board(new RussianCheckersRule()));
  }

  @Test
    void testFindFiguresBetweenToBeat(){
      System.out.println(gameState);
      assertTrue(gameState.isOnlyOneFigureBetweenToBeat(new Cell('B', 3), new Cell('F', 7)));
  }


}
