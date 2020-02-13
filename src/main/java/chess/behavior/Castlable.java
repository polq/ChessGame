package chess.behavior;

public interface Castlable {

  default boolean castle() {
    return true;
  }
}
