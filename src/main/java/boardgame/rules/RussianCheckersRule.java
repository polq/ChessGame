package boardgame.rules;

import boardgame.items.board.Cell;
import boardgame.items.figures.checkers.Checker;
import boardgame.player.Player;

import java.util.HashMap;
import java.util.Map;

public class RussianCheckersRule extends GameRule {
  private static final int BOARD_WEIGHT = 8;
  private static final int BOARD_HEIGHT = 8;

  private static String WHITE_CHECKER_MAN = "\u2686"; //  ⚆
  private static String BLACK_CHECKER_MAN = "\u2688"; //  ⚈
  private static String WHITE_CHECKER_KING = "\u2654"; // ♔
  private static String BLACK_CHECKER_KING = "\u265A"; // ♚

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
    Player whitePlayer = new Player("white",1);
    Player blackPlayer = new Player("black", -1);
    Map<String, Cell> standartCheckersCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        Cell newCell = new Cell((char) j, i);
        if (i == GameRule.initialBoardHeight) {
          newCell.setChangeable(true);
          if (j == 'B' || j == 'D' || j == 'F' || j == 'H') {
            newCell.setFigure(new Checker(whitePlayer, WHITE_CHECKER_MAN));
            newCell.getFigure().setNewIcon(WHITE_CHECKER_KING);
          } else {
            newCell.setEmpty(true);
          }
        } else if (i == getBoardHeight()) {
          newCell.setChangeable(true);
          if (j == 'A' || j == 'C' || j == 'E' || j == 'G') {
            newCell.setFigure(new Checker(blackPlayer, BLACK_CHECKER_MAN));
            newCell.getFigure().setNewIcon(BLACK_CHECKER_KING);
          }else {
            newCell.setEmpty(true);
          }
        } else if (i == 2 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          newCell.setFigure(new Checker(whitePlayer, WHITE_CHECKER_MAN));
          newCell.getFigure().setNewIcon(WHITE_CHECKER_KING);
        } else if (i == 6 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          newCell.setFigure(new Checker(blackPlayer, BLACK_CHECKER_MAN));
          newCell.getFigure().setNewIcon(BLACK_CHECKER_KING);
        } else if (i == 3 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          newCell.setFigure(new Checker(whitePlayer, WHITE_CHECKER_MAN));
          newCell.getFigure().setNewIcon(WHITE_CHECKER_KING);
        } else if (i == 7 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          newCell.setFigure(new Checker(blackPlayer, BLACK_CHECKER_MAN));
          newCell.getFigure().setNewIcon(BLACK_CHECKER_KING);
        } else {
          newCell.setEmpty(true);
        }

        String cellKey = newCell.getStringKey();
        standartCheckersCells.put(cellKey, newCell);
      }
    }
    return standartCheckersCells;
  }
}
