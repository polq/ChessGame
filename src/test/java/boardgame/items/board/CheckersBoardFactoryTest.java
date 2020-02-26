package boardgame.items.board;

import boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CheckersBoardFactoryTest {

  BoardFactory rule;

  @BeforeEach
  public void init() {
    rule =
        new CheckersBoardFactory(
            (LinkedList<Player>) (Arrays.asList(new Player("white"), new Player("black"))));
  }

  @Test
  void getInitialBoard() {
    Board board =
        BoardFactory.createBoard(
            "checkers",
            (LinkedList<Player>) (Arrays.asList(new Player("white"), new Player("black"))));
    assertEquals(64, board.getBoardCells().size());
  }
}
