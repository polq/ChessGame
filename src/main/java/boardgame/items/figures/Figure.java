package boardgame.items.figures;

import boardgame.items.boardcell.Cell;
import boardgame.player.Player;

public abstract class Figure {

  private Player figureOwner;
  private boolean isMoved;
  private boolean isChangeable;
  private boolean isCastlable;

  public Figure(Player figureOwner) {
    this.figureOwner = figureOwner;
    this.isMoved = false;
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


  /**
   * Method is used to determinate if specified {@link Figure} can move from one {@link Cell} to
   * another
   *
   * @param start represent initial position of the {@link Figure}
   * @param destination represents where {@link Figure} should move
   * @return true if figure can move from one cell to another and false - if not.
   * @throws NullPointerException in case param values are null
   * @throws IllegalArgumentException in case start and destination {@link Cell} are equal
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
   * @throws IllegalArgumentException in case start and destination {@link Cell} are equal
   */
  public boolean canBeat(Cell start, Cell destination) {
    return this.canMove(start, destination);
  }

  public String getIconStringName(){
    return this.getFigureOwner().toString() + "_" + this.toString();
  }

  @Override
  public String toString() {
    return this.getFigureOwner().toString();
  }
}
