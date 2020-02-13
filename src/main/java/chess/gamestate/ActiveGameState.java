package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.player.ChessPlayer;

import java.util.Map;
import java.util.Set;

public class ActiveGameState extends GameState {

  public ActiveGameState(Board board) {
    super(board);
  }

  @Override
  public GameState switchGameState() {
    ChessPlayer currentPlayer = getCurrentTurnPlayer();
    if (isUnderCheck(currentPlayer, findKing(currentPlayer))) {
      if (isUnderCheckMate(currentPlayer, findKing(currentPlayer))) {
        return new CheckMateGameState(getGameBoard());
      } else {
        return new CheckGameState(getGameBoard());
      }
    } else {
      if (isUnderCheckMate(currentPlayer, findKing(currentPlayer))
          && getAliveFigures(currentPlayer).size() <= 1) {
        return new DrawGameState(getGameBoard());
      } else {
        return this;
      }
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
