package chess.items.figures;

import chess.behavior.Actionable;
import chess.items.board.Cell;
import chess.player.Player;

public abstract class Figure implements Actionable {

  Player chessOwner;
  String chessIcon;
  boolean isMoved;
  String newIcon;

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

  @Override
  public boolean beat(Cell start, Cell destination) {
    return this.move(start, destination);
  }

  @Override
  public String toString() {
    return this.getChessOwner().toString();
  }
}
