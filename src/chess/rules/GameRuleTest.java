package chess.rules;

import chess.items.board.Board;
import org.junit.jupiter.api.Test;


class GameRuleTest {

  @Test
  void getInitialBoardState() {

    Board board = new Board(new StandardChessRule());

    System.out.println(board);
  }
}