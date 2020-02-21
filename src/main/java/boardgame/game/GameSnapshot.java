package boardgame.game;

import boardgame.items.boardcell.Board;
import boardgame.items.boardcell.BoardFactory;
import boardgame.items.boardcell.Cell;

import java.util.stream.IntStream;

public class GameSnapshot {

  private static String EMPTY_CELL = "\u25A1";

  private boolean isActive;
  private String gameStatusMessage;
  private Board board;

  private GameSnapshot() {}

  public boolean isActive() {
    return isActive;
  }

  static GameSnapshot buildSuccessCommandSnap(GameAI gameAI) {
    GameSnapshot snapshot = new GameSnapshot();
    snapshot.gameStatusMessage = gameAI.getGameStatus();
    snapshot.isActive = gameAI.isActive();
    snapshot.board = gameAI.getGameBoard();
    return snapshot;
  }

  static GameSnapshot buildErrorSnap(String errorMessage) {
    GameSnapshot gameSnapshot = new GameSnapshot();
    gameSnapshot.gameStatusMessage = errorMessage;
    gameSnapshot.isActive = true;
    return gameSnapshot;
  }

  static GameSnapshot buildJustStartedGameSnap(GameAI gameAI) {
    GameSnapshot gameSnapshot = new GameSnapshot();
    gameSnapshot.board = gameAI.getGameBoard();
    gameSnapshot.gameStatusMessage =
        "Game has been started, it's "
            + gameAI.getCurrentTurnPlayer()
            + " turn. \nPlease type in cell coordinates that identify figure that "
            + "you want to move and position where the figure should be move, separated"
            + " either by space or following symbols: '-', '/', '|', '\\'";
    gameSnapshot.isActive = true;
    return gameSnapshot;
  }

  public String getStringGameSnap() {
    if (board == null) {
      return gameStatusMessage;
    }

    StringBuilder builder = new StringBuilder("  ");
    int firstLetter = BoardFactory.initialBoardWeight;
    int lastLetter = firstLetter + board.getBoardWeight();
    IntStream.range(firstLetter, lastLetter).forEach(i -> builder.append((char) i).append(" "));
    builder.append("\n");

    for (int i = board.getBoardHeight(); i >= 1; i--) {
      builder.append(i);
      builder.append(" ");
      for (int j = firstLetter; j < lastLetter; j++) {
        String cellKey = "" + (char) j + i;
        Cell cell = board.getBoardCells().get(cellKey);
        if (cell.isEmpty()) {
          builder.append(EMPTY_CELL);
        } else {
          String iconName = cell.getFigure().getIconStringName();
          builder.append(board.getFigureIcons().get(iconName));
        }
        builder.append(" ");
      }
      builder.append("\n");
    }
    builder.append(gameStatusMessage);
    return builder.toString();
  }
}
