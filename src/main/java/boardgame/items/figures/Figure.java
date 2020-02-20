package boardgame.items.figures;

import boardgame.items.cell.Cell;
import boardgame.player.Player;

public abstract class Figure {

  private Player figureOwner;
  private String chessIcon;
  private boolean isMoved;
  private String newIcon;
  private boolean isChangeable;

  public Figure(Player figureOwner, String chessIcon) {
    this.figureOwner = figureOwner;
    this.chessIcon = chessIcon;
    this.isMoved = false;
  }

  public Figure(Player figureOwner) {
    this.figureOwner = figureOwner;
    this.isMoved = false;
  }

  public String getChessIcon() {
    return this.chessIcon;
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

  public String getNewIcon() {
    return newIcon;
  }

  public void setNewIcon(String newIcon) {
    this.newIcon = newIcon;
  }

  public boolean isChangeable() {
    return isChangeable;
  }

  public void setCanBeChanged(boolean canBeChanged) {
    this.isChangeable = canBeChanged;
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
  public boolean move(Cell start, Cell destination) {
    if (start == null || destination == null) {
      throw new NullPointerException("Invalid null arguments");
    }

    if (start.equals(destination)) {
      throw new IllegalArgumentException("Method arguments cannot be equal");
    }
    return false;
  }

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
  public boolean beat(Cell start, Cell destination) {
    return this.move(start, destination);
  }

  @Override
  public String toString() {
    return this.getFigureOwner().toString();
  }
}
