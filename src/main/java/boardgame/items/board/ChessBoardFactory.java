package boardgame.items.board;

import boardgame.items.figures.chess.Bishop;
import boardgame.items.figures.chess.King;
import boardgame.items.figures.chess.Knight;
import boardgame.items.figures.chess.Pawn;
import boardgame.items.figures.chess.Queen;
import boardgame.items.figures.chess.Rook;
import boardgame.player.Player;

import java.util.*;

public class ChessBoardFactory extends BoardFactory {

  private static final int BOARD_WIDTH = 8;
  private static final int BOARD_HEIGHT = 8;
  Player firstPlayer;
  Player secondPlayer;

  ChessBoardFactory(LinkedList<Player> playerQueue) {
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
    iconsMap.put(firstPlayer + "_ROOK", "\u2656");
    iconsMap.put(secondPlayer + "_ROOK", "\u265C");
    iconsMap.put(firstPlayer + "_QUEEN", "\u2655");
    iconsMap.put(secondPlayer + "_QUEEN", "\u265B");
    iconsMap.put(firstPlayer + "_PAWN", "\u2659");
    iconsMap.put(secondPlayer + "_PAWN", "\u265F");
    iconsMap.put(firstPlayer + "_KNIGHT", "\u2658");
    iconsMap.put(secondPlayer + "_KNIGHT", "\u265E");
    iconsMap.put(firstPlayer + "_KING", "\u2654");
    iconsMap.put(secondPlayer + "_KING", "\u265A");
    iconsMap.put(firstPlayer + "_BISHOP", "\u2657");
    iconsMap.put(secondPlayer + "_BISHOP", "\u265D");
    return iconsMap;
  }

  @Override
  Map<String, Cell> generateBoardCells() {
    Map<String, Cell> standardChessCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WIDTH; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        Cell.Builder builder = new Cell.Builder((char) j, i);
        Cell newCell;
        if (i == 1 || i == getBoardHeight()) {
          Player player = (i == 1) ? firstPlayer : secondPlayer;
          if (j == 'A' || j == 'H') {
            newCell = builder.withFigure(new Rook(player)).changeable(true).build();
          } else if (j == 'B' || j == 'G') {
            newCell = builder.withFigure(new Knight(player)).changeable(true).build();
          } else if (j == 'C' || j == 'F') {
            newCell = builder.withFigure(new Bishop(player)).changeable(true).build();
          } else if (j == 'D') {
            newCell = builder.withFigure(new King(player)).changeable(true).build();
          } else if (j == 'E') {
            newCell = builder.withFigure(new Queen(player)).changeable(true).build();
          } else {
            newCell = builder.empty(true).build();
          }
        } else if (i == 2) {
          newCell = builder.withFigure(new Pawn(firstPlayer)).build();
        } else if (i == getBoardHeight() - 1) {
          newCell = builder.withFigure(new Pawn(secondPlayer)).build();
        } else {
          newCell = builder.empty(true).build();
        }
        String cellKey = newCell.getStringKey();
        standardChessCells.put(cellKey, newCell);
      }
    }
    return standardChessCells;
  }
}
