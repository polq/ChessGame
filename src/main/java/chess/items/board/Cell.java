package chess.items.board;

import chess.items.figures.Figure;

import java.util.Objects;

public class Cell {

  private final int positionNumber;
  private final char positionLetter;
  private Figure figure;
  private boolean isEmpty;
  private boolean isChangeable;

  Cell(char positionLetter, int positionNumber, Figure figure, boolean isEmpty) {
    this.positionLetter = Character.toUpperCase(positionLetter);
    this.positionNumber = positionNumber;
    this.figure = figure;
    this.isEmpty = isEmpty;
  }

  public Cell(char positionLetter, int positionNumber) {
    this(positionLetter, positionNumber, null, false);
  }

  public Figure getFigure() {
    return figure;
  }

  public void setFigure(Figure figure) {
    this.figure = figure;
  }

  public void setEmpty(boolean empty) {
    isEmpty = empty;
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

  public void setChangeable(boolean changeable) {
    isChangeable = changeable;
  }

  public void figureMovedFromThisCell() {
    this.isEmpty = true;
    this.figure = null;
  }

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
