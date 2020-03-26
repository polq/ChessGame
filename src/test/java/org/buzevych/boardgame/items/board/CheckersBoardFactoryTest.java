package org.buzevych.boardgame.items.board;

import org.buzevych.boardgame.items.board.Board;
import org.buzevych.boardgame.items.board.BoardFactory;
import org.buzevych.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CheckersBoardFactoryTest {

  Board board;

  @BeforeEach
  public void init() {
    LinkedList<Player> list = new LinkedList<>();
    list.add(new Player("white"));
    list.add(new Player("black"));
    board = BoardFactory.createBoard("checkers", list);
  }

  @Test
  void getInitialBoard() {
    assertEquals(64, board.getBoardCells().size());
  }
}
