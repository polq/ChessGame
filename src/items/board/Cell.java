package items.board;

import items.chesspieces.ChessFigure;

import java.util.Objects;

public class Cell {

  private int positionNumber;
  private char positionLetter;
  private ChessFigure figure;
  private boolean isEmpty;

  Cell(int positionNumber, char positionLetter, ChessFigure figure, boolean isEmpty) {
    this.positionNumber = positionNumber;
    this.positionLetter = positionLetter;
    this.figure = figure;
    this.isEmpty = isEmpty;
  }

  public Cell(int positionNumber, char positionLetter) {
    this.positionNumber = positionNumber;
    this.positionLetter = positionLetter;
  }

  public Cell(int positionNumber, char positionLetter, ChessFigure figure) {
    this(positionNumber, positionLetter, figure, false);
  }

  public ChessFigure getFigure() {
    return figure;
  }

  public void setFigure(ChessFigure figure) {
    this.figure = figure;
  }

  public void setEmpty(boolean empty) {
    isEmpty = empty;
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
    return isEmpty ? "\u25A1" : figure.getItemIcon();
  }
}
