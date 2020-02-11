package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

import java.util.Map;

public class NotStartedGameState extends GameState {

  public NotStartedGameState(Board board) {
    super(board);
  }

  @Override
  public GameState switchGameState() {
    return new ActiveGameState(this.getGameBoard());
  }

  @Override
  public void executeCommand(String fromCoordinate, String toCoordinate) {
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    Cell fromCell = gameBoardCells.get(fromCoordinate);
    Cell toCell = gameBoardCells.get(toCoordinate);
    ChessPlayer currentTurnPlayer = getCurrentTurnPlayer();

    if (fromCell == null || toCell == null) {
      throw new IllegalArgumentException("Invalid input arguments");
    }

    if (fromCell.isEmpty() || !toCell.isEmpty()) {
      throw new IllegalArgumentException(
          "Invalid input arguments, the first argument must represent chess figure and the second - empty spot on a board");
    }

    // Check if currentTurnPlayer matches figure owner
    if (!checkOwner(fromCell.getFigure())) {
      throw new IllegalArgumentException(
          "Invalid input argument, you can only move figures of your own color");
    }

    ChessFigure figure = fromCell.getFigure();
    if (figure.move(fromCell, toCell)) {
      // Modifying cell from where chess piece has moved
      fromCell.figureMovedFromThisCell();
      // Modifying cell to where chess has moved
      toCell.figureMovedToThisCell(figure);

      // Updating values on board
      gameBoardCells.put(fromCoordinate, fromCell);
      gameBoardCells.put(toCoordinate, toCell);

      //ChangeTurn
      switchPlayer();
    }
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
