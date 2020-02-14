package chess.gamestate;

import chess.behavior.Castlable;
import chess.behavior.Jumpable;
import chess.behavior.Queenable;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.items.chesspieces.king.King;
import chess.items.chesspieces.queen.Queen;
import chess.items.chesspieces.rook.Rook;
import chess.player.ChessPlayer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class GameState {

  private Board gameBoard;

  public GameState(Board board) {
    this.gameBoard = board;
  }

  Board getGameBoard() {
    return gameBoard;
  }

  // switches game states depending on situation on board
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
        return new ActiveGameState(getGameBoard());
      }
    }
  }

  // gets players that should play now
  ChessPlayer getCurrentTurnPlayer() {
    return gameBoard.getPlayersQueue().peek();
  }

  // gives turn to the next player
  public ChessPlayer switchPlayer() {
    ChessPlayer previousPlayer = gameBoard.getPlayersQueue().remove();
    gameBoard.getPlayersQueue().add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  // checks if figure belongs to current player
  boolean checkOwner(ChessFigure figure) {
    return figure.getChessOwner().getClass().equals(getCurrentTurnPlayer().getClass());
  }


  public void executeCommand(String fromCoordinate, String toCoordinate) {}

  // move from one cell to another empty cell
  void executeMove(Cell fromCell, Cell toCell) {
    ChessFigure figure = fromCell.getFigure();

    // Check if figure can move from the given cell to the destination one and there are no
    // obstacles on their path

    if (!figure.move(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    switchFigure(fromCell, toCell, figure);
  }

  // switch figure on board
  void switchFigure(Cell fromCell, Cell toCell, ChessFigure figure) {
    // Modifying cell from where chess piece has moved
    fromCell.figureMovedFromThisCell();
    // Modifying cell to where chess has moved
    toCell.figureMovedToThisCell(figure);

    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(figure.getChessOwner(), findKing(figure.getChessOwner()))) {
      toCell.figureMovedFromThisCell();
      fromCell.figureMovedToThisCell(figure);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }

    figure.setMoved(true);
    // if figure that can become a Queen moves to a queenable cell
    becomeQueen(figure, toCell);
  }

  // checks if figure is allowed to become queen
  void becomeQueen(ChessFigure previousFigure, Cell toCell) {
    if (previousFigure instanceof Queenable && toCell instanceof Queenable) {
      ChessPlayer player = previousFigure.getChessOwner();
      toCell.setFigure(new Queen(player, player.getQueenIcon()));
    }
  }

  // executes beating
  void executeBeat(Cell fromCell, Cell toCell) {
    ChessFigure figure = fromCell.getFigure();
    ChessFigure figureToBeat = toCell.getFigure();
    if (!figure.beat(fromCell, toCell) || !isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot beat from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    // Modifying cell from where chess piece has moved
    fromCell.figureMovedFromThisCell();
    // Modifying cell to where chess has moved
    toCell.figureMovedToThisCell(figure);

    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(figure.getChessOwner(), findKing(figure.getChessOwner()))) {
      toCell.figureMovedToThisCell(figureToBeat);
      fromCell.figureMovedToThisCell(figure);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }

    figure.setMoved(true);
    becomeQueen(figure, toCell);
  }

  // castle rook and king
  void executeCastle(Cell fromCell, Cell toCell) {
    if (fromCell.getFigure() == null || toCell.getFigure() == null) {
      throw new NullPointerException("Null arguments while castling");
    }

    if (!(fromCell.getFigure() instanceof Castlable)
        || !(toCell.getFigure() instanceof Castlable)) {
      throw new IllegalArgumentException("You can only castle King and Rook");
    }

    Cell kingCell;
    Cell rookCell;

    if (fromCell.getFigure() instanceof Rook && !(toCell.getFigure() instanceof Rook)) {
      rookCell = fromCell;
      kingCell = toCell;
    } else if (fromCell.getFigure() instanceof King) {
      kingCell = fromCell;
      rookCell = toCell;
    } else {
      throw new IllegalArgumentException("You cannot castle two rooks");
    }

    King king = (King) kingCell.getFigure();
    Rook rook = (Rook) rookCell.getFigure();

    if (!rook.move(fromCell, toCell)) {
      throw new IllegalArgumentException("Castling figures should be on the same line");
    }

    if (!king.castle()) {
      throw new IllegalArgumentException("You cannot castle a king that has moved");
    }

    kingCell.figureMovedToThisCell(rook);
    rookCell.figureMovedToThisCell(king);

    // Check if move has lead to own king check and rollback if true
    if (isUnderCheck(king.getChessOwner(), findKing(king.getChessOwner()))) {
      kingCell.figureMovedToThisCell(king);
      rookCell.figureMovedToThisCell(rook);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
  }

  // checks if players king is under check
  boolean isUnderCheck(ChessPlayer player, Cell kingCell) {
    List<Cell> enemyfigures =
        gameBoard.getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getChessOwner().getClass().equals(player.getClass()))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .collect(Collectors.toList());

    return !enemyfigures.isEmpty();
  }

  // gets all figures on board by player
  Set<ChessFigure> getAliveFigures(ChessPlayer player) {
    return gameBoard.getBoardCells().entrySet().stream()
        .map(Map.Entry::getValue)
        .filter(Predicate.not(Cell::isEmpty))
        .map(Cell::getFigure)
        .filter(figure -> figure.getChessOwner().getClass().equals(player.getClass()))
        .collect(Collectors.toSet());
  }

  // find cell where king is located
  Cell findKing(ChessPlayer player) {
    return getGameBoard().getBoardCells().entrySet().stream()
        .filter(entry -> !entry.getValue().isEmpty())
        .filter(
            entry ->
                entry.getValue().getFigure().getChessOwner().getClass().equals(player.getClass()))
        .filter(entry -> entry.getValue().getFigure() instanceof King)
        .map(Map.Entry::getValue)
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No king on the board, game is already over"));
  }

  // checks if players king is under check mate
  boolean isUnderCheckMate(ChessPlayer player, Cell kingCell) {
    // First check if figures that check our king can be eliminated
    List<Cell> enemyFiguresUnderCheck =
        gameBoard.getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getChessOwner().getClass().equals(player.getClass()))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .filter(cell -> isUnderCheck(cell.getFigure().getChessOwner(), cell))
            .collect(Collectors.toList());

    if (!enemyFiguresUnderCheck.isEmpty()) {
      return false;
    }

    // Then check if there any empty cells where king can move
    List<Cell> potentialEmptyCellList =
        gameBoard.getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(entry -> !entry.equals(kingCell))
            .filter(Cell::isEmpty)
            .filter(entry -> kingCell.getFigure().move(kingCell, entry))
            .collect(Collectors.toList());

    Optional<Cell> potentialUncheckedCells =
        potentialEmptyCellList.stream().filter(cell -> !isUnderCheck(player, cell)).findAny();

    return potentialUncheckedCells.isEmpty();
  }

  // checks if figure can move from start point to end point without any obstacles
  boolean isPathClear(Cell startPoint, Cell endPoint, ChessFigure figure) {

    if (startPoint == null || endPoint == null || figure == null) {
      throw new NullPointerException();
    }

    if (startPoint.equals(endPoint)) {
      throw new IllegalArgumentException("Start and End Points must be unique");
    }

    // Jumpable figures does not care about obstacles on their path
    if (figure instanceof Jumpable) {
      return true;
    }

    // numbers identical - horizontal movement
    if (startPoint.getPositionNumber() == endPoint.getPositionNumber()) {
      int min = Integer.min(startPoint.getPositionLetter(), endPoint.getPositionLetter());
      int abs = Math.abs(startPoint.getPositionLetter() - endPoint.getPositionLetter());

      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath =
            gameBoard.getBoardCells().get("" + (char) i + startPoint.getPositionNumber());
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else if (startPoint.getPositionLetter()
        == endPoint.getPositionLetter()) { // letters identical - vertical movement
      int min = Integer.min(startPoint.getPositionNumber(), endPoint.getPositionNumber());
      int abs = Math.abs(startPoint.getPositionNumber() - endPoint.getPositionNumber());

      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath = gameBoard.getBoardCells().get("" + startPoint.getPositionLetter() + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else {
      int numberDifference = endPoint.getPositionNumber() - startPoint.getPositionNumber();
      int letterDifference = endPoint.getPositionLetter() - startPoint.getPositionLetter();

      /*chess pieces can only move vertically, horizontally and by diagonal (where difference between letters and
      numbers must be identical */
      if (Math.abs(numberDifference) != Math.abs(letterDifference)) {
        return false;
      }

      int numberStep = Math.abs(numberDifference) / numberDifference;
      int letterStep = Math.abs(letterDifference) / letterDifference;

      for (int i = startPoint.getPositionNumber() + numberStep,
              j = startPoint.getPositionLetter() + letterStep;
          i != endPoint.getPositionNumber();
          i += numberStep, j += letterStep) {
        Cell cellOnPath = gameBoard.getBoardCells().get("" + (char) j + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public String toString() {
    return gameBoard.toString();
  }
}
