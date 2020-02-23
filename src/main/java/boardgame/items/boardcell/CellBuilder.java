package boardgame.items.boardcell;

import boardgame.items.figures.Figure;

/**
 * Builder Class that is used to build a specified Cell object and initialize Cell's field. Has
 * methods to build build an object and to get a result.
 */
public class CellBuilder {

  private Cell cell;

  public CellBuilder(char letterCoordinate, int numberCoordinate) {
    this.cell = new Cell(letterCoordinate, numberCoordinate);
  }

  /**
   *  Method that is used to get a cell result after it's building.
   * @return {@link Cell} object
   */
  public Cell getResultCell() {
    return this.cell;
  }

  void buildEmptyCell() {
    this.cell.setEmpty(true);
    this.cell.setFigure(null);
    this.cell.setChangeable(false);
  }

  void buildChangeAbleEmptyCell() {
    this.cell.setEmpty(true);
    this.cell.setFigure(null);
    this.cell.setChangeable(true);
  }

  void buildFigureCell(Figure figure) {
    this.cell.setEmpty(false);
    this.cell.setFigure(figure);
    this.cell.setChangeable(false);
  }

  void buildChangeableFigureCell(Figure figure) {
    this.cell.setEmpty(false);
    this.cell.setFigure(figure);
    this.cell.setChangeable(true);
  }

}
