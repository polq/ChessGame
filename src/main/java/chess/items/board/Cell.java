package chess.items.board;

import chess.items.chesspieces.ChessFigure;

import java.util.Objects;

public class Cell {

  private final int positionNumber;
  private final char positionLetter;
  private ChessFigure figure;
  private boolean isEmpty;

  Cell(char positionLetter, int positionNumber, ChessFigure figure, boolean isEmpty) {
    this.positionLetter = Character.toUpperCase(positionLetter);
    this.positionNumber = positionNumber;
    this.figure = figure;
    this.isEmpty = isEmpty;
  }

  public Cell(char positionLetter, int positionNumber) {
    this(positionLetter, positionNumber, null, false);
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

  public boolean isEmpty() {
    return isEmpty;
  }

  public int getPositionNumber() {
    return positionNumber;
  }

  public char getPositionLetter() {
    return positionLetter;
  }

  public void figureMovedFromThisCell() {
    this.isEmpty = true;
    this.figure = null;
  }

  public void figureMovedToThisCell(ChessFigure figure) {
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
