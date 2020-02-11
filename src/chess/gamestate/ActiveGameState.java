package chess.gamestate;

import chess.items.board.Board;

public class ActiveGameState extends GameState {

  public ActiveGameState(Board board) {
    super(board);
  }

  @Override
  public GameState switchGameState() {
    return null;
  }

  @Override
  public void executeCommand(String fromCoordinate, String toCoordinate) {

  }

  @Override
  public String toString() {
    return "In active";
  }
}
