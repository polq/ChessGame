package boardgame.rules;

import boardgame.items.board.Cell;
import boardgame.items.figures.chess.Bishop;
import boardgame.items.figures.chess.King;
import boardgame.items.figures.chess.Knight;
import boardgame.items.figures.chess.Pawn;
import boardgame.items.figures.chess.Queen;
import boardgame.items.figures.chess.Rook;
import boardgame.player.Player;

import java.util.HashMap;
import java.util.Map;

public class StandardChessRule extends GameRule {

  private static final int BOARD_WEIGHT = 8;
  private static final int BOARD_HEIGHT = 8;

  private static final String WHITE_ROOK_ICON = "\u2656";
  private static final String BLACK_ROOK_ICON = "\u265C";
  private static final String WHITE_QUEEN_ICON = "\u2655";
  private static final String BLACK_QUEEN_ICON = "\u265B";
  private static final String WHITE_PAWN_ICON = "\u2659";
  private static final String BLACK_PAWN_ICON = "\u265F";
  private static final String WHITE_KNIGHT_ICON = "\u2658";
  private static final String BLACK_KNIGHT_ICON = "\u265E";
  private static final String WHITE_KING_ICON = "\u2654";
  private static final String BLACK_KING_ICON = "\u265A";
  private static final String WHITE_BISHOP_ICON = "\u2657";
  private static final String BLACK_BISHOP_ICON = "\u265D";

  @Override
  public int getBoardWeight() {
    return BOARD_WEIGHT;
  }

  @Override
  public int getBoardHeight() {
    return BOARD_HEIGHT;
  }

  @Override
  public Map<String, Cell> generateBoardCells() {
    Player whitePlayer = new Player("white");
    Player blackPlayer = new Player("black");
    Map<String, Cell> standardChessCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        Cell newCell = new Cell((char) j, i);

        if (i == GameRule.initialBoardHeight) {
          newCell.setChangeable(true);
          if (j == 'A' || j == 'H') {
            newCell.setFigure(new Rook(whitePlayer, WHITE_ROOK_ICON));
          } else if (j == 'B' || j == 'G') {
            newCell.setFigure(new Knight(whitePlayer, WHITE_KNIGHT_ICON));
          } else if (j == 'C' || j == 'F') {
            newCell.setFigure(new Bishop(whitePlayer, WHITE_BISHOP_ICON));
          } else if (j == 'D') {
            newCell.setFigure(new King(whitePlayer, WHITE_KING_ICON));
          } else if (j == 'E') {
            newCell.setFigure(new Queen(whitePlayer, WHITE_QUEEN_ICON));
          }
        } else if (i == getBoardHeight()) {
          newCell.setChangeable(true);
          if (j == 'A' || j == 'H') {
            newCell.setFigure(new Rook(blackPlayer, BLACK_ROOK_ICON));
          } else if (j == 'B' || j == 'G') {
            newCell.setFigure(new Knight(blackPlayer, BLACK_KNIGHT_ICON));
          } else if (j == 'C' || j == 'F') {
            newCell.setFigure(new Bishop(blackPlayer, BLACK_BISHOP_ICON));
          } else if (j == 'D') {
            newCell.setFigure(new King(blackPlayer, BLACK_KING_ICON));
          } else if (j == 'E') {
            newCell.setFigure(new Queen(blackPlayer, BLACK_QUEEN_ICON));
          }
        } else if (i == GameRule.initialBoardHeight + 1) {
          newCell.setFigure(new Pawn(new Player("white",1), WHITE_PAWN_ICON));
          newCell.getFigure().setNewIcon(WHITE_QUEEN_ICON);
        } else if (i == getBoardHeight() - 1) {
          newCell.setFigure(new Pawn(new Player("black",-1), BLACK_PAWN_ICON));
          newCell.getFigure().setNewIcon(BLACK_QUEEN_ICON);
        } else {
          newCell.setEmpty(true);
        }
        String cellKey = newCell.getStringKey();
        standardChessCells.put(cellKey, newCell);
      }
    }
    return standardChessCells;
  }
}
