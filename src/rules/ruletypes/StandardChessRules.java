package rules.ruletypes;

import items.board.Cell;
import items.chesspieces.bishop.BlackBishop;
import items.chesspieces.bishop.WhiteBishop;
import items.chesspieces.king.BlackKing;
import items.chesspieces.king.WhiteKing;
import items.chesspieces.knight.BlackKnight;
import items.chesspieces.knight.WhiteKnight;
import items.chesspieces.pawn.BlackPawn;
import items.chesspieces.pawn.WhitePawn;
import items.chesspieces.queen.BlackQueen;
import items.chesspieces.queen.WhiteQueen;
import items.chesspieces.rook.BlackRook;
import items.chesspieces.rook.WhiteRook;
import rules.GameRule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StandardChessRules extends GameRule {

  private static final int PLAYER_COUNT = 2;
  private static final int BOARD_WEIGHT = 8;
  private static final int BOARD_HEIGHT = 8;

  @Override
  public int getPlayerCount() {
    return PLAYER_COUNT;
  }

  @Override
  public int getBoardWeight() {
    return BOARD_WEIGHT;
  }

  @Override
  public int getBoardHeight() {
    return BOARD_HEIGHT;
  }

  @Override
  public Map<String, Cell> getInitialBoardState() {
    Map<String, Cell> standardChessCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        Cell newCell = new Cell(i, (char) j);

        if (i == 1) {
          if (j == 'A' || j == 'H') {
            newCell.setFigure(new WhiteRook());
          } else if (j == 'B' || j == 'G') {
            newCell.setFigure(new WhiteKnight());
          } else if (j == 'C' || j == 'F') {
            newCell.setFigure(new WhiteBishop());
          } else if (j == 'D') {
            newCell.setFigure(new WhiteKing());
          } else if (j == 'E') {
            newCell.setFigure(new WhiteQueen());
          }
        } else if (i == 8) {
          if (j == 'A' || j == 'H') {
            newCell.setFigure(new BlackRook());
          } else if (j == 'B' || j == 'G') {
            newCell.setFigure(new BlackKnight());
          } else if (j == 'C' || j == 'F') {
            newCell.setFigure(new BlackBishop());
          } else if (j == 'D') {
            newCell.setFigure(new BlackKing());
          } else if (j == 'E') {
            newCell.setFigure(new BlackQueen());
          }
        } else if (i == 2) {
          newCell.setFigure(new WhitePawn());
        } else if (i == 7) {
          newCell.setFigure(new BlackPawn());
        } else {
          newCell.setEmpty(true);
        }
        String cellKey = ""+ i + (char) j;
        standardChessCells.put(cellKey, newCell);
      }
    }
    return standardChessCells;
  }
}
