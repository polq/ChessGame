package boardgame.items.board;

import boardgame.items.cell.Cell;
import boardgame.items.cell.CellBuilder;
import boardgame.items.figures.checkers.Checker;
import boardgame.player.Player;
import java.util.HashMap;
import java.util.Map;

public class CheckersBoardFactory extends BoardFactory {
  private static final int BOARD_WEIGHT = 8;
  private static final int BOARD_HEIGHT = 8;

  @Override
  int getBoardWeight() {
    return BOARD_WEIGHT;
  }

  @Override
  int getBoardHeight() {
    return BOARD_HEIGHT;
  }

  @Override
  Map<String, String> generateFigureIcons() {
    Map<String, String> iconsMap = new HashMap<>();
    iconsMap.put("WHITE_CHECKER", "\u2686"); //  ⚆
    iconsMap.put("BLACK_CHECKER", "\u2688"); //  ⚈
    iconsMap.put("WHITE_CHECKERKING", "\u2654"); // ♔
    iconsMap.put("BLACK_CHECKERKING", "\u265A"); // ♚
    return iconsMap;
  }

  @Override
  Map<String, Cell> generateBoardCells() {
    Player whitePlayer = new Player("white", 1);
    Player blackPlayer = new Player("black", -1);
    Map<String, Cell> standardCheckersCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        CellBuilder builder = new CellBuilder((char) j, i);
        if (i == BoardFactory.initialBoardHeight) {
          if (j == 'B' || j == 'D' || j == 'F' || j == 'H') {
            builder.buildChangeableFigureCell(new Checker(whitePlayer));
          } else {
            builder.buildChangeAbleEmptyCell();
          }
        } else if (i == getBoardHeight()) {
          if (j == 'A' || j == 'C' || j == 'E' || j == 'G') {
            builder.buildChangeableFigureCell(new Checker(whitePlayer));
          } else {
            builder.buildChangeAbleEmptyCell();
          }
        } else if (i == 2 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          builder.buildFigureCell(new Checker(whitePlayer));
        } else if (i == 6 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          builder.buildFigureCell(new Checker(blackPlayer));
        } else if (i == 3 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          builder.buildFigureCell(new Checker(whitePlayer));
        } else if (i == 7 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          builder.buildFigureCell(new Checker(blackPlayer));
        } else {
          builder.buildEmptyCell();
        }
        Cell newCell = builder.getResultCell();
        String cellKey = newCell.getStringKey();
        standardCheckersCells.put(cellKey, newCell);
      }
    }
    return standardCheckersCells;
  }
}
