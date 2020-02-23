package boardgame.items.boardcell;

import java.util.Map;

/**
 * Abstract Factory to create a {@link Board} object with the specified weight, height and board
 * Cells.
 * <p>
 * Has one public method that build and returns board.
 */
public abstract class BoardFactory {

  public static final char initialBoardWeight = 'A';
  public static final int initialBoardHeight = 1;

  /**
   * Method that is used to build a {@link Board} object
   *
   * @return {@link Board} with all field already initiated
   */
  public Board createBoard() {
    Board board = new Board();
    board.setBoardCells(this.generateBoardCells());
    board.setBoardHeight(this.getBoardHeight());
    board.setBoardWeight(this.getBoardWeight());
    board.setFigureIcons(this.generateFigureIcons());
    return board;
  }

  abstract int getBoardWeight();

  abstract int getBoardHeight();

  abstract Map<String, String> generateFigureIcons();

  /**
   * Creates initial {@link boardgame.items.boardcell.Board} state including position of all {@link
   * boardgame.items.figures.Figure} on the Board for the defined game rule
   *
   * @return {@link Map} with {@link String} key representing coordinate on the {@link
   * boardgame.items.boardcell.Board} and {@link Cell} Value representing all corresponding
   * properties of the coordinate
   */
  abstract Map<String, Cell> generateBoardCells();
}
