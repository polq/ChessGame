package boardgame.game;

import boardgame.items.boardcell.Board;
import boardgame.items.figures.Figure;
import boardgame.player.Player;
import java.util.LinkedList;
import java.util.Queue;

public abstract class GameAI {

  static final String gameRuleDelimiters = "[- \\\\./|]";

  Board gameBoard;
  Queue<Player> playerQueue;

  Board getGameBoard() {
    return gameBoard;
  }

  Player switchPlayer() {
    Player previousPlayer = playerQueue.remove();
    playerQueue.add(previousPlayer);
    return getCurrentTurnPlayer();
  }

  Player getCurrentTurnPlayer() {
    return playerQueue.peek();
  }

  Queue<Player> generatePlayerQueue() {
    Queue<Player> playersQueue = new LinkedList<>();
    playersQueue.add(new Player("white"));
    playersQueue.add(new Player("black"));
    return playersQueue;
  }

  boolean checkFigureOwner(Figure figure) {
    return figure.getFigureOwner().equals(getCurrentTurnPlayer());
  }

  abstract boolean isActive();

  abstract void executeCommand(String inputString);

  abstract String getGameStatus();
}
