package chess.items.board;

import chess.behavior.Queenable;
import chess.items.chesspieces.ChessFigure;

public class QueenableCell extends Cell implements Queenable {

  QueenableCell(char positionLetter, int positionNumber, ChessFigure figure, boolean isEmpty) {
    super(positionLetter, positionNumber, figure, isEmpty);
  }

  public QueenableCell(char positionLetter, int positionNumber) {
    super(positionLetter, positionNumber);
  }
}
