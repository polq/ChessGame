package chess.gamestate;

import chess.exception.GameOverException;
import chess.items.board.Board;

public class CheckMateGameState extends GameState {

  public CheckMateGameState(Board board) {
    super(board);
  }

  @Override
  public String toString() {
    throw new GameOverException(
        super.toString() + "Checkmate, " + getCurrentTurnPlayer() + " has lost!");
  }
}
