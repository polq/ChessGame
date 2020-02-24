package boardgame.player;

import java.util.Objects;

/**
 * Player class is used to distinguish figure pieces by it's owner and to determinate amount and
 * type of players that can play a board game.
 */
public class Player {

  private int defaultStep;
  private String playerName;

  public Player(String playerName, int defaultStep) {
    this.playerName = playerName;
    this.defaultStep = defaultStep;
  }

  public Player(String playerName) {
    this(playerName, 0);
  }

  public int getDefaultStep() {
    return defaultStep;
  }

  /**
   * Method to check if players are identical. Method takes player names as a benchmark and equals
   * them
   *
   * @param o another Player object
   * @return boolean value representing of players are equal
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return playerName.equalsIgnoreCase(player.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerName);
  }

  @Override
  public String toString() {
    return playerName.toUpperCase();
  }
}
