package boardgame.items.figures;

import boardgame.behavior.Actionable;
import boardgame.items.board.Cell;
import boardgame.player.Player;

public abstract class Figure implements Actionable {

  private Player chessOwner;
  private String chessIcon;
  private boolean isMoved;
  private String newIcon;

  public Figure(Player chessOwner, String chessIcon) {
    this.chessOwner = chessOwner;
    this.chessIcon = chessIcon;
    this.isMoved = false;
  }

  public String getChessIcon() {
    return this.chessIcon;
  }

  public Player getChessOwner() {
    return chessOwner;
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
  @Override
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
  @Override
  public boolean beat(Cell start, Cell destination) {
    return this.move(start, destination);
  }

  @Override
  public String toString() {
    return this.getChessOwner().toString();
  }
}
