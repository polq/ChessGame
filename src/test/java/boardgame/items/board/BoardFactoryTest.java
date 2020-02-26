package boardgame.items.board;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

class BoardFactoryTest {

  @Test
  void createChessBoard() {
    Board chessBoard = BoardFactory.createBoard("chess", (LinkedList<Player>) (Arrays.asList(new Player("white"), new Player("black"))));

    assertEquals(64, chessBoard.getBoardHeight() * chessBoard.getBoardWeight());
    assertEquals(64, chessBoard.getBoardCells().size());
  }

  @Test
  void createCheckersBoard() {
    Board chessBoard = BoardFactory.createBoard("chess", (LinkedList<Player>) (Arrays.asList(new Player("white"), new Player("black"))));

    assertEquals(4, chessBoard.getFigureIcons().size());
    assertEquals(12, chessBoard.getAliveFigures(new Player("white")).size());
  }
}
