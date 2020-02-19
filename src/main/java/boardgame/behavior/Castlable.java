package boardgame.behavior;

public interface Castlable {

  default boolean castle() {
    return true;
  }
}
