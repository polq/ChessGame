package org.buzevych.boardgame.items.board;

import org.buzevych.boardgame.items.figures.Figure;

import java.util.Objects;

/**
 * Class represents a single cell unit on the board with the unchangeable parameters such as
 * positionNumber and positionLetter. Additionally class has parameters to determinate is cell is
 * empty what figure is located in it right now.
 *
 * <p>{@link Cell} class has methods to move a figure to a current cell and to move a figure from a
 * cell.
 */
public class Cell {

  private int positionNumber;
  private char positionLetter;
  private Figure figure;
  private boolean isEmpty;
  private boolean isChangeable;

  private Cell() {}

  /** Method used to changed Cell to an empty one */
  void figureMovedFromThisCell() {
    this.isEmpty = true;
    this.figure = null;
  }

  /**
   * Method is used to cet Figure to the current {@link Cell}
   *
   * @param figure represents {@link Figure} that has moved to this {@link Cell}
   */
  void figureMovedToThisCell(Figure figure) {
    this.figure = figure;
    this.isEmpty = false;
  }

  /**
   * Method is used to get String representation of the vertical and horizontal Cell coordinates
   *
   * @return {@link String} representation of the {@link Cell} coordinates
   */
  public String getStringKey() {
    return "" + Character.toUpperCase(this.getPositionLetter()) + this.getPositionNumber();
  }

  /**
   * Method is used to check if {@link Cell's} are equal
   *
   * @param o another {@link Cell} object
   * @return true in case positionNumber and positionLetter for both Cell's are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Cell cell = (Cell) o;
    return positionNumber == cell.positionNumber && positionLetter == cell.positionLetter;
  }

  @Override
  public int hashCode() {
    return Objects.hash(positionNumber, positionLetter);
  }

  public Figure getFigure() {
    return figure;
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

  /**
   * Builder class to create a new Cell object, where Cell's number and letter are required params.
   */
  public static class Builder {

    private int positionNumber;
    private char positionLetter;
    private Figure figure;
    private boolean isEmpty;
    private boolean isChangeable;

    public Builder(char positionLetter, int positionNumber) {
      this.positionLetter = positionLetter;
      this.positionNumber = positionNumber;
    }

    public Builder withFigure(Figure figure) {
      this.figure = figure;
      return this;
    }

    public Builder empty(boolean isEmpty) {
      this.isEmpty = isEmpty;
      return this;
    }

    public Builder changeable(boolean isChangeable) {
      this.isChangeable = isChangeable;
      return this;
    }

    public Cell build() {
      Cell cell = new Cell();
      cell.positionLetter = this.positionLetter;
      cell.positionNumber = this.positionNumber;
      cell.figure = this.figure;
      cell.isEmpty = this.isEmpty;
      cell.isChangeable = this.isChangeable;
      return cell;
    }
  }
}
