package boardgame.items.board;

import boardgame.items.figures.checkers.Checker;
import boardgame.player.Player;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/** Concrete realization of the BoardFactory class to build Checkers Board */
public class CheckersBoardFactory extends BoardFactory {
  private static final int BOARD_WIDTH = 8;
  private static final int BOARD_HEIGHT = 8;

  Player firstPlayer;
  Player secondPlayer;

  CheckersBoardFactory(LinkedList<Player> playerQueue) {
    this.firstPlayer = playerQueue.get(0);
    this.secondPlayer = playerQueue.get(1);
    this.firstPlayer.setDefaultStep(1);
    this.secondPlayer.setDefaultStep(-1);
  }

  @Override
  int getBoardWidth() {
    return BOARD_WIDTH;
  }

  @Override
  int getBoardHeight() {
    return BOARD_HEIGHT;
  }

  @Override
  Map<String, String> generateFigureIcons() {
    Map<String, String> iconsMap = new HashMap<>();
    iconsMap.put(firstPlayer + "_CHECKER", "\u2686"); //  ⚆
    iconsMap.put(secondPlayer + "_CHECKER", "\u2688"); //  ⚈
    iconsMap.put(firstPlayer + "_CHECKERKING", "\u2654"); // ♔
    iconsMap.put(secondPlayer + "_CHECKERKING", "\u265A"); // ♚
    return iconsMap;
  }

  @Override
  Map<String, Cell> generateBoardCells() {
    Map<String, Cell> standardCheckersCells = new HashMap<>();
    for (int i = 1; i <= BOARD_HEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_WIDTH; j++) {
        Cell newCell;
        Cell.Builder builder = new Cell.Builder((char) j, i);
        if (i == 1) {
          if (j == 'B' || j == 'D' || j == 'F' || j == 'H') {
            newCell = builder.withFigure(new Checker(firstPlayer)).changeable(true).build();
          } else {
            newCell = builder.empty(true).changeable(true).build();
          }
        } else if (i == BOARD_HEIGHT) {
          if (j == 'A' || j == 'C' || j == 'E' || j == 'G') {
            newCell = builder.withFigure(new Checker(secondPlayer)).changeable(true).build();
          } else {
            newCell = builder.empty(true).changeable(true).build();
          }
        } else if (i == 2 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          newCell = builder.withFigure(new Checker(firstPlayer)).build();
        } else if (i == 6 && (j == 'A' || j == 'C' || j == 'E' || j == 'G')) {
          newCell = builder.withFigure(new Checker(secondPlayer)).build();
        } else if (i == 3 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          newCell = builder.withFigure(new Checker(firstPlayer)).build();
        } else if (i == 7 && (j == 'B' || j == 'D' || j == 'F' || j == 'H')) {
          newCell = builder.withFigure(new Checker(secondPlayer)).build();
        } else {
          newCell = builder.empty(true).changeable(true).build();
        }
        String cellKey = newCell.getStringKey();
        standardCheckersCells.put(cellKey, newCell);
      }
    }
    return standardCheckersCells;
  }
}
