package boardgame.player;

import java.util.Objects;

public class Player {

  private int defaultStep;
  private String playerName;

  public Player(String playerName, int defaultStep){
    this.playerName = playerName;
    this.defaultStep = defaultStep;
  }
  public Player(String playerName) {
    this(playerName, 0);
  }
  
  public int getDefaultStep() {
    return defaultStep;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Player player = (Player) o;
    return playerName.equals(player.playerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(playerName);
  }
}
