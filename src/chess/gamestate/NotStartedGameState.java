package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;

import java.util.Map;

public class NotStartedGameState extends GameState {

  public NotStartedGameState(Board board) {
    super(board);
  }

  @Override
  public GameState switchGameState() {
    // when first move is successful - game is started and goes into Active state
    return new ActiveGameState(this.getGameBoard());
  }

  @Override
  public void executeCommand(String fromCoordinate, String toCoordinate) {
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    Cell fromCell = gameBoardCells.get(fromCoordinate);
    Cell toCell = gameBoardCells.get(toCoordinate);

    if (fromCell == null || toCell == null) {
      throw new NullPointerException("Invalid input arguments");
    }

    if (fromCell.isEmpty() || !toCell.isEmpty()) {
      throw new IllegalArgumentException(
              "Invalid input arguments, the first argument must represent chess figure and the second - empty spot on a board");
    }

    // Check if currentTurnPlayer matches figure owner
    if (!checkOwner(fromCell.getFigure())) {
      throw new IllegalCallerException(
              "Invalid input argument, you can only move figures of your own color");
    }
    // there is only one possible action at the start of the game - to move to an empty cell
    executeMove(fromCell, toCell);
  }

  @Override
  public String toString() {
    return "Game has been successfully started\n"
        + super.toString()
        + "\nIt's "
        + super.getCurrentTurnPlayer()
        + " turn";
  }
}
