package boardgame.items.cell;

import boardgame.items.figures.Figure;

public class CellBuilder {

  private Cell cell;

  public CellBuilder(char letterCoordinate, int numberCoordinate) {
    this.cell = new Cell(letterCoordinate, numberCoordinate);
  }

  public void buildEmptyCell() {
    this.cell.setEmpty(true);
    this.cell.setFigure(null);
    this.cell.setChangeable(false);
  }

  public void buildChangeAbleEmptyCell() {
    this.cell.setEmpty(true);
    this.cell.setFigure(null);
    this.cell.setChangeable(true);
  }

  public void buildFigureCell(Figure figure) {
    this.cell.setEmpty(false);
    this.cell.setFigure(figure);
    this.cell.setChangeable(false);
  }

  public void buildChangeableFigureCell(Figure figure) {
    this.cell.setEmpty(false);
    this.cell.setFigure(figure);
    this.cell.setChangeable(true);
  }

  public Cell getResultCell() {
    return this.cell;
  }
}
