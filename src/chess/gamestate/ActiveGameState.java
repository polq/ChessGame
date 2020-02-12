package chess.gamestate;

import chess.behavior.Queenable;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;

import java.util.Map;

public class ActiveGameState extends GameState {

  public ActiveGameState(Board board) {
    super(board);
  }

  @Override
  public GameState switchGameState() {
    if (isUnderCheck(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
      return new CheckGameState(getGameBoard());
    } else {
      return this;
    }
  }

  @Override
  public void executeCommand(String fromCoordinate, String toCoordinate) {
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    Cell fromCell = gameBoardCells.get(fromCoordinate);
    Cell toCell = gameBoardCells.get(toCoordinate);

    if (fromCell == null || toCell == null) {
      throw new NullPointerException("Invalid input arguments");
    }

    ChessFigure fromFigure = fromCell.getFigure();
    // check if first argument is a figure and current player owns it
    if (fromFigure == null || !checkOwner(fromFigure)) {
      throw new IllegalArgumentException("The first argument must represent current player figure");
    }

    if (toCell.isEmpty()) {
      executeMove(fromCell, toCell);
    } else {
      // if toCell contains player's own figure - try to execute Castle
      if (checkOwner(toCell.getFigure())) {
        executeCastle(fromCell, toCell);
      } else {
        executeBeat(fromCell, toCell);
      }
    }
  }

  @Override
  public String toString() {
    return super.toString() + "\nIt's " + super.getCurrentTurnPlayer() + " turn";
  }
}
