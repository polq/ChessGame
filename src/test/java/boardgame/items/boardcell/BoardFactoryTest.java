package boardgame.items.boardcell;

import static org.junit.jupiter.api.Assertions.*;

import boardgame.player.Player;
import org.junit.jupiter.api.Test;

class BoardFactoryTest {

  @Test
  void createChessBoard() {
    BoardFactory factory = new ChessBoardFactory();
    Board chessBoard = factory.createBoard();

    assertEquals(64, chessBoard.getBoardHeight() * chessBoard.getBoardWeight());
    assertEquals(64, chessBoard.getBoardCells().size());
  }

  @Test
  void createCheckersBoard() {
    BoardFactory factory = new CheckersBoardFactory();
    Board chessBoard = factory.createBoard();

    assertEquals(4, chessBoard.getFigureIcons().size());
    assertEquals(12, chessBoard.getAliveFigures(new Player("white")).size());
  }
}
