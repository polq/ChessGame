package chess.rules;

import chess.items.board.Cell;
import chess.items.chesspieces.king.King;
import chess.player.BlackPlayer;
import chess.player.ChessPlayer;
import chess.player.WhitePlayer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
* Game Rule for testing purpose
* with 2x2 board
*
*    A B
*  2 ♔ □
*  1 □ ♚
*/

public class CheckGameRule extends GameRule {
  @Override
  public int getBoardWeight() {
    return 2;
  }

  @Override
  public int getBoardHeight() {
    return 2;
  }

  @Override
  public Map<String, Cell> getInitialBoard() {
    ChessPlayer whitePlayer = new WhitePlayer();
    ChessPlayer blackPlayer = new BlackPlayer();
    Map<String, Cell> map = new HashMap<>();
    Cell emptyCell = new Cell('A', 1);
    emptyCell.setEmpty(true);
    map.put(emptyCell.getStringKey(), emptyCell);
    emptyCell = new Cell('B', 2);
    emptyCell.setEmpty(true);
    map.put(emptyCell.getStringKey(), emptyCell);
    Cell kingCell = new Cell('A', 2);
    kingCell.setFigure(new King(whitePlayer, "\u2654"));
    map.put(kingCell.getStringKey(), kingCell);
    kingCell = new Cell('B', 1);
    kingCell.setFigure(new King(blackPlayer, "\u265A"));
    map.put(kingCell.getStringKey(), kingCell);
    return map;
  }

  @Override
  public Queue<ChessPlayer> getInitialPlayersQueue() {
    Queue<ChessPlayer> chessPlayersQueue = new LinkedList<>();
    chessPlayersQueue.add(new WhitePlayer());
    chessPlayersQueue.add(new BlackPlayer());
    return chessPlayersQueue;
  }
}
