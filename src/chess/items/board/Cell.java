package chess.items.board;

import chess.items.chesspieces.ChessFigure;

import java.util.Objects;

public class Cell {

  public int getPositionNumber() {
    return positionNumber;
  }

  public char getPositionLetter() {
    return positionLetter;
  }

  private final int positionNumber;
  private final char positionLetter;
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

  public boolean isEmpty() {
    return isEmpty;
  }

  public void figureMovedFromThisCell(){
    this.isEmpty = true;
    this.figure = null;
  }

  public void figureMovedToThisCell(ChessFigure figure){
    this.figure = figure;
    this.isEmpty = false;
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
