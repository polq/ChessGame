package boardgame.items.board;

import boardgame.items.cell.Cell;
import boardgame.items.cell.CellBuilder;
import boardgame.items.figures.chess.Bishop;
import boardgame.items.figures.chess.King;
import boardgame.items.figures.chess.Knight;
import boardgame.items.figures.chess.Pawn;
import boardgame.items.figures.chess.Queen;
import boardgame.items.figures.chess.Rook;
import boardgame.player.Player;
import java.util.HashMap;
import java.util.Map;

public class ChessBoardFactory extends BoardFactory {

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
    iconsMap.put("WHITE_ROOK", "\u2656");
    iconsMap.put("BLACK_ROOK", "\u265C");
    iconsMap.put("WHITE_QUEEN", "\u2655");
    iconsMap.put("BLACK_QUEEN", "\u265B");
    iconsMap.put("WHITE_PAWN", "\u2659");
    iconsMap.put("BLACK_PAWN", "\u265F");
    iconsMap.put("WHITE_KNIGHT", "\u2658");
    iconsMap.put("BLACK_KNIGHT", "\u265E");
    iconsMap.put("WHITE_KING", "\u2654");
    iconsMap.put("BLACK_KING", "\u265A");
    iconsMap.put("WHITE_BISHOP", "\u2657");
    iconsMap.put("BLACK_BISHOP", "\u265D");
    return iconsMap;
  }

  @Override
  Map<String, Cell> generateBoardCells() {
    Player whitePlayer = new Player("white", 1);
    Player blackPlayer = new Player("black", -1);
    Map<String, Cell> standardChessCells = new HashMap<>();
    for (int i = 1; i <= BOARD_WEIGHT; i++) {
      for (int j = 'A'; j < 'A' + BOARD_HEIGHT; j++) {
        CellBuilder builder = new CellBuilder((char) j, i);
        if (i == BoardFactory.initialBoardHeight || i == getBoardHeight()) {
          Player player =
              (i == BoardFactory.initialBoardHeight) ? whitePlayer : blackPlayer;
          if (j == 'A' || j == 'H') {
            builder.buildChangeableFigureCell(new Rook(player));
          } else if (j == 'B' || j == 'G') {
            builder.buildChangeableFigureCell(new Knight(player));
          } else if (j == 'C' || j == 'F') {
            builder.buildChangeableFigureCell(new Bishop(player));
          } else if (j == 'D') {
            builder.buildChangeableFigureCell(new King(player));
          } else if (j == 'E') {
            builder.buildChangeableFigureCell(new Queen(player));
          }
        } else if (i == BoardFactory.initialBoardHeight + 1) {
          builder.buildFigureCell(new Pawn(whitePlayer));
        } else if (i == getBoardHeight() - 1) {
          builder.buildFigureCell(new Pawn(blackPlayer));
        } else {
          builder.buildEmptyCell();
        }
        Cell cell = builder.getResultCell();
        String cellKey = cell.getStringKey();
        standardChessCells.put(cellKey, cell);
      }
    }
    return standardChessCells;
  }
}
