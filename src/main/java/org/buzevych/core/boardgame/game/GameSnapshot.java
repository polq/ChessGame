package org.buzevych.core.boardgame.game;

import org.buzevych.core.boardgame.items.board.Board;
import org.buzevych.core.boardgame.items.board.Cell;

import java.util.stream.IntStream;

/**
 * Public class that is used to get the snapShot representation of the game status to the client in
 * the specified formats. The class has several static package-private methods for building snapshot
 * representation depending on the successfullness of the commands. Additionally class has public
 * method identifying if the game is still active.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class GameSnapshot {

  private boolean active;
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
    return active;
  }

  public Board getBoard() {
    return board;
  }

  public String[][] htmlBoardRepresentation() {
    String[][] cells = new String[this.board.getBoardHeight()+1][this.board.getBoardWeight()+1];
    int firstLetter = 'A';
    int lastLetter = firstLetter + board.getBoardWeight();
    for (int i = board.getBoardHeight(); i >= 1; i--) {
      cells[i][0] = board.getBoardHeight() - i + 1 + "";
      cells[0][board.getBoardWeight()-i +1] = (char) (firstLetter + board.getBoardWeight()-i) + "";
      for (int j = firstLetter; j < lastLetter; j++) {
        String cellKey = "" + (char) j + i;
        Cell cell = board.getBoardCells().get(cellKey);
        if (!cell.isEmpty()) {
          String iconName = cell.getFigure().getIconStringName();
          int matrixI = board.getBoardHeight() - i;
          int matrixJ = j - firstLetter;
          cells[matrixI+1][matrixJ+1] = board.getFigureIcons().get(iconName);
        }
      }
    }
    return cells;
  }

  public String getGameStatusMessage() {
    return gameStatusMessage;
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
    int firstLetter = 'A';
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
          String emptyCell = "\u25A1";
          builder.append(emptyCell);
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

  static class Builder {

    private boolean active;
    private String gameStatusMessage;
    private Board board;

    Builder() {
      active = true;
    }

    Builder withGameAI(GameAI gameAI){
      this.active = gameAI.isActive();
      this.board = gameAI.getGameBoard();
      this.gameStatusMessage = gameAI.getGameStatus();
      return this;
    }

    Builder end() {
      this.active = false;
      return this;
    }

    Builder withBoard(Board board) {
      this.board = board;
      return this;
    }

    Builder withGameMessage(String gameStatusMessage) {
      this.gameStatusMessage = gameStatusMessage;
      return this;
    }

    GameSnapshot build() {
      GameSnapshot gameSnapshot = new GameSnapshot();
      gameSnapshot.active = this.active;
      gameSnapshot.board = this.board;
      gameSnapshot.gameStatusMessage = this.gameStatusMessage;
      return gameSnapshot;
    }
  }
}
