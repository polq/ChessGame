package chess.rules;

import chess.items.board.Cell;
import chess.player.ChessPlayer;
import java.util.Map;
import java.util.Queue;

public abstract class GameRule {

  public static final char initialBoardWeight = 'A';
  public static final int initialBoardHeight = 1;
  public static final String gameRuleDelimiters = "[- \\\\./|]";

  public abstract int getBoardWeight();

  public abstract int getBoardHeight();

  public abstract Map<String, Cell> getInitialBoard();

  public abstract Queue<ChessPlayer> getInitialPlayersQueue();
}
