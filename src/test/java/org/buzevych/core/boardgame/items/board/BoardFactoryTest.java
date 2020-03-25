package org.buzevych.core.boardgame.items.board;

import static org.junit.jupiter.api.Assertions.*;

import org.buzevych.core.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class BoardFactoryTest {

  LinkedList<Player> list;

  @BeforeEach
  void init() {
    list = new LinkedList<>();
    list.add(new Player("white"));
    list.add(new Player("black"));
  }

  @Test
  void createChessBoard() {
    Board chessBoard = BoardFactory.createBoard("chess", list);
    assertEquals(64, chessBoard.getBoardHeight() * chessBoard.getBoardWeight());
    assertEquals(64, chessBoard.getBoardCells().size());
  }

  @Test
  void createCheckersBoard() {
    Board chessBoard = BoardFactory.createBoard("checkers", list);
    assertEquals(4, chessBoard.getFigureIcons().size());
    assertEquals(12, chessBoard.getAliveFigures(new Player("white")).size());
  }
}
