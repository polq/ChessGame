package chess.behavior;

// Marker interface to tag chess piece that can become queen and cell
public interface Queenable {
  default String getQueenIcon() {
    return null;
  }
}
