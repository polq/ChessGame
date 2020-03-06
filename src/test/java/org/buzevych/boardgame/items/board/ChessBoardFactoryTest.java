package org.buzevych.boardgame.items.board;

import org.buzevych.boardgame.items.board.BoardFactory;
import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.items.board.ChessBoardFactory;
import org.buzevych.boardgame.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardFactoryTest {

  BoardFactory boardFactory;

  @BeforeEach
  void init() {
    LinkedList<Player> list = new LinkedList<>();
    list.add(new Player("white"));
    list.add(new Player("black"));
    boardFactory = new ChessBoardFactory(list);
  }

  @Test
  void testGetInitialBoard() {
    Map<String, Cell> boardMap = boardFactory.generateBoardCells();
    long figuresNumber = boardMap.values().stream().filter(cell -> !cell.isEmpty()).count();

    assertEquals(64, boardMap.size());
    assertEquals(32, figuresNumber);
  }
}
