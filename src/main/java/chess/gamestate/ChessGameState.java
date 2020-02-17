package chess.gamestate;

import chess.behavior.Castlable;
import chess.behavior.Changeable;
import chess.behavior.Jumpable;
import chess.exception.GameOverException;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.figures.Figure;
import chess.items.figures.chess.King;
import chess.items.figures.chess.Queen;
import chess.items.figures.chess.Rook;
import chess.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChessGameState extends GameState {
  public ChessGameState(Board board) {
    super(board);
  }

  @Override
  public String getGameStatus() {
    String result = "It's " + getCurrentTurnPlayer() + " turn.";
    if (isUnderCheck(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))) {
        throw new GameOverException("GameOver " + getCurrentTurnPlayer() + " has lost");
      } else {
        return result + getCurrentTurnPlayer() + " is under check";
      }
    } else {
      if (isUnderCheckMate(getCurrentTurnPlayer(), findKing(getCurrentTurnPlayer()))
          && getAliveFigures(getCurrentTurnPlayer()).size() <= 1) {
        throw new GameOverException("GameOver. It's a draw");
      } else {
        return result;
      }
    }
  }

  @Override
  public void executeCommand(String[] inputCoordinates) {
    String fromCoordinate = inputCoordinates[0];
    String toCoordinate = inputCoordinates[1];
    Map<String, Cell> gameBoardCells = getGameBoard().getBoardCells();
    Cell fromCell = gameBoardCells.get(fromCoordinate);
    Cell toCell = gameBoardCells.get(toCoordinate);

    if (fromCell == null || toCell == null) {
      throw new NullPointerException("Invalid input arguments");
    }

    Figure fromFigure = fromCell.getFigure();
    // check if first argument is a figure and current player owns it
    if (fromFigure == null || !checkFigureOwner(fromFigure)) {
      throw new IllegalArgumentException("The first argument must represent current player figure");
    }

    if (toCell.isEmpty()) {
      executeMove(fromCell, toCell);
    } else {
      // if toCell contains player's own figure - try to execute Castle
      if (checkFigureOwner(toCell.getFigure())) {
        executeCastle(fromCell, toCell);
      } else {
        executeBeat(fromCell, toCell);
      }
    }
  }

  @Override
  void executeMove(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    if (!isPathClear(fromCell, toCell, figure)) {
      throw new IllegalArgumentException(
          "Invalid input "
              + figure.toString()
              + " cannot move from "
              + fromCell.getStringKey()
              + " to "
              + toCell.getStringKey());
    }

    moveFigure(fromCell, toCell);

    if (isUnderCheck(figure.getChessOwner(), findKing(figure.getChessOwner()))) {
      toCell.figureMovedFromThisCell();
      fromCell.figureMovedToThisCell(figure);
      throw new IllegalArgumentException("Invalid move, your king is under check");
    }
    figure.setMoved(true);
    becomeQueen(figure, toCell);
  }

  @Override
  void executeBeat(Cell fromCell, Cell toCell) {
    Figure figure = fromCell.getFigure();
    Figure figureToBeat = toCell.getFigure();
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

  void executeCastle(Cell fromCell, Cell toCell) {
    if (fromCell.getFigure() == null || toCell.getFigure() == null) {
      throw new NullPointerException("Null arguments while castling");
    }

    if (!(fromCell.getFigure() instanceof Castlable)
        || !(toCell.getFigure() instanceof Castlable)) {
      throw new IllegalArgumentException("You cannot beat your own figures");
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

  void becomeQueen(Figure previousFigure, Cell toCell) {
    if (previousFigure instanceof Changeable && toCell.isChangeable()) {
      Player player = previousFigure.getChessOwner();
      toCell.setFigure(new Queen(player, player.getNewFigureIcon()));
    }
  }

  private boolean isUnderCheck(Player player, Cell kingCell) {
    List<Cell> enemyfigures =
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(cell -> !cell.isEmpty())
            .filter(cell -> !cell.getFigure().getChessOwner().getClass().equals(player.getClass()))
            .filter(cell -> cell.getFigure().beat(cell, kingCell))
            .filter(cell -> isPathClear(cell, kingCell, cell.getFigure()))
            .collect(Collectors.toList());

    return !enemyfigures.isEmpty();
  }

  private boolean isUnderCheckMate(Player player, Cell kingCell) {
    // First check if figures that check our king can be eliminated
    List<Cell> enemyFiguresUnderCheck =
        getGameBoard().getBoardCells().entrySet().stream()
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
        getGameBoard().getBoardCells().entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(entry -> !entry.equals(kingCell))
            .filter(Cell::isEmpty)
            .filter(entry -> kingCell.getFigure().move(kingCell, entry))
            .collect(Collectors.toList());

    Optional<Cell> potentialUncheckedCells =
        potentialEmptyCellList.stream().filter(cell -> !isUnderCheck(player, cell)).findAny();

    return potentialUncheckedCells.isEmpty();
  }

  private Cell findKing(Player player) {
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

  private boolean isPathClear(Cell startPoint, Cell endPoint, Figure figure) {

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
            getGameBoard().getBoardCells().get("" + (char) i + startPoint.getPositionNumber());
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    } else if (startPoint.getPositionLetter()
        == endPoint.getPositionLetter()) { // letters identical - vertical movement
      int min = Integer.min(startPoint.getPositionNumber(), endPoint.getPositionNumber());
      int abs = Math.abs(startPoint.getPositionNumber() - endPoint.getPositionNumber());

      for (int i = min + 1; i < min + abs; i++) {
        Cell cellOnPath =
            getGameBoard().getBoardCells().get("" + startPoint.getPositionLetter() + i);
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
        Cell cellOnPath = getGameBoard().getBoardCells().get("" + (char) j + i);
        if (!cellOnPath.isEmpty()) {
          return false;
        }
      }
    }

    return true;
  }
}
