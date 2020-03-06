package org.buzevych.boardgame.items.figures;

import org.buzevych.boardgame.items.board.Cell;
import org.buzevych.boardgame.player.Player;
import org.buzevych.boardgame.items.board.Board;

/**
 * Figure class represents a class that identifies a figure on a board game. It has proper fields
 * that identify a figure and methods that check if figure can move or beat in the specified
 * direction.
 */
public abstract class Figure {

  private Player figureOwner;
  private boolean isMoved;
  private boolean isChangeable;
  private boolean isCastlable;

  public Figure(Player figureOwner) {
    this.figureOwner = figureOwner;
    this.isMoved = false;
  }

  /**
   * Method is used to determinate if specified {@link Figure} can move from one {@link Cell} to
   * another
   *
   * @param start represent initial position of the {@link Figure}
   * @param destination represents where {@link Figure} should move
   * @return true if figure can move from one cell to another and false - if not.
   * @throws NullPointerException in case param values are null
   */
  public abstract boolean canMove(Cell start, Cell destination);

  /**
   * Method is used to determinate if specified {@link Figure} can beat from one {@link Cell} to
   * another
   *
   * @param start represent initial position of the {@link Figure}
   * @param destination represents where {@link Figure} should move
   * @return true if figure can beat from one cell to another and false - if not.
   * @throws NullPointerException in case param values are null
   */
  public boolean canBeat(Cell start, Cell destination) {
    return this.canMove(start, destination);
  }

  /**
   * Method that is used to return a name for an figure icon in a format of [PlayerOwner_Figure]
   *
   * @return {@link String} that can find an icon in a {@link Board} {@code
   *     figureIcons} list
   */
  public String getIconStringName() {
    return this.getFigureOwner().toString() + "_" + this.toString();
  }

  public Player getFigureOwner() {
    return figureOwner;
  }

  public boolean isMoved() {
    return isMoved;
  }

  public void setMoved(boolean moved) {
    isMoved = moved;
  }

  public boolean isChangeable() {
    return isChangeable;
  }

  public void setCanBeChanged(boolean canBeChanged) {
    this.isChangeable = canBeChanged;
  }

  public boolean isCastlable() {
    return isCastlable;
  }

  public void setCastlable(boolean castlable) {
    isCastlable = castlable;
  }
}
