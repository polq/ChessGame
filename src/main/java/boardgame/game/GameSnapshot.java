package boardgame.game;

import boardgame.items.boardcell.Board;
import boardgame.items.boardcell.BoardFactory;
import boardgame.items.boardcell.Cell;

import java.util.stream.IntStream;

/**
 * Public class that is used to get the snapShot representation of the game status to the client in
 * the specified formats. The class has several static package-private methods for building snapshot
 * representation depending on the successfullness of the commands. Additionally class has public
 * method identifying if the game is still active.
 * <p>
 * Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class GameSnapshot {

  private static String EMPTY_CELL = "\u25A1";
  private boolean isActive;
  private String gameStatusMessage;
  private Board board;

  private GameSnapshot() {
  }

  /**
   * Method is used to determinate if the game is still active and has not ended.
   *
   * @return true if the game is still ongoing, false - if it has ended and no more commands are
   * necessary
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Method is used to get the String representation of the {@link Board} and the whole game status
   * at the current moment of the game state. In case the {@link Board} object is null, only status
   * message will be shown.
   *
   * @return {@link String} that can be shown to a client as a result of an command execution.
   */
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
}
