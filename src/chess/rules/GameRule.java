package chess.rules;

import chess.items.board.Cell;
import chess.player.ChessPlayer;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public abstract class GameRule {

  public final static char initialBoardWeight = 'A';
  public final static int initialBoardHeight = 1;
  public final static String gameRuleDelimiters = "[- \\\\./|]";

  public abstract int getBoardWeight();

  public abstract int getBoardHeight();

  public abstract Map<String, Cell> getInitialBoard();

  public abstract Queue<ChessPlayer> getInitialPlayersQueue();
}
