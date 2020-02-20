package boardgame.items.cell;

import boardgame.items.figures.Figure;

import java.util.Objects;

public class Cell {

  private final int positionNumber;
  private final char positionLetter;
  private Figure figure;
  private boolean isEmpty;
  private boolean isChangeable;

  Cell(char positionLetter, int positionNumber) {
    this.positionLetter = positionLetter;
    this.positionNumber = positionNumber;
  }

  public Figure getFigure() {
    return figure;
  }

  void setFigure(Figure figure) {
    this.figure = figure;
  }

  void setEmpty(boolean empty) {
    isEmpty = empty;
  }

  void setChangeable(boolean changeable) {
    isChangeable = changeable;
  }

  public boolean isEmpty() {
    return isEmpty;
  }

  public int getPositionNumber() {
    return positionNumber;
  }

  public char getPositionLetter() {
    return positionLetter;
  }

  public boolean isChangeable() {
    return isChangeable;
  }

  /** Method used to changed Cell to an empty one */
  public void figureMovedFromThisCell() {
    this.isEmpty = true;
    this.figure = null;
  }

  /**
   * Method is used to cet Figure to the current {@link Cell}
   *
   * @param figure represents {@link Figure} that has moved to this {@link Cell}
   */
  public void figureMovedToThisCell(Figure figure) {
    this.figure = figure;
    this.isEmpty = false;
  }

  public String getStringKey() {
    return "" + Character.toUpperCase(this.getPositionLetter()) + this.getPositionNumber();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cell cell = (Cell) o;
    return positionNumber == cell.positionNumber && positionLetter == cell.positionLetter;
  }

  @Override
  public int hashCode() {
    return Objects.hash(positionNumber, positionLetter);
  }

  @Override
  public String toString() {
    return isEmpty ? "\u25A1" : figure.getChessIcon();
  }
}
