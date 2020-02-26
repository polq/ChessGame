package boardgame.items.board;

import boardgame.player.Player;

import java.util.LinkedList;
import java.util.Map;

/**
 * Abstract Factory to create a {@link Board} object with the specified weight, height and board
 * Cells.
 *
 * <p>Has one public method that build and returns board.
 */
public abstract class BoardFactory {

  /**
   * Method that is used to build a {@link Board} object
   *
   * @return {@link Board} with all field already initiated
   */
  public static Board createBoard(String gameName, LinkedList<Player> playerQueue) {
    Board board = new Board();
    BoardFactory factory;
    if (gameName.equals("chess")) {
      factory = new ChessBoardFactory(playerQueue);
    } else if (gameName.equals("checkers")) {
      factory = new CheckersBoardFactory(playerQueue);
    } else {
      throw new IllegalArgumentException("Wrong game name");
    }
    board.setBoardCells(factory.generateBoardCells());
    board.setBoardHeight(factory.getBoardHeight());
    board.setBoardWeight(factory.getBoardWidth());
    board.setFigureIcons(factory.generateFigureIcons());
    return board;
  }

  abstract int getBoardWidth();

  abstract int getBoardHeight();

  abstract Map<String, String> generateFigureIcons();

  /**
   * Creates initial {@link boardgame.items.board.Board} state including position of all {@link
   * boardgame.items.figures.Figure} on the Board for the defined game rule
   *
   * @return {@link Map} with {@link String} key representing coordinate on the {@link
   *     boardgame.items.board.Board} and {@link Cell} Value representing all corresponding
   *     properties of the coordinate
   */
  abstract Map<String, Cell> generateBoardCells();
}
