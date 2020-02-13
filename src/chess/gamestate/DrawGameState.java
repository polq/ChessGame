package chess.gamestate;

import chess.exception.GameOverException;
import chess.items.board.Board;

public class DrawGameState extends GameState {

  public DrawGameState(Board board) {
    super(board);
  }

  @Override
  public String toString() {
    throw new GameOverException(super.toString() + "It's a draw.");
  }
}
