package boardgame.game;

/**
 * The class represents main class to start working with the board games. It has a private
 * constructor and static method to start a new game. Additionally it has methods to play a game
 * that returns {@link GameSnapshot} and a method that returns {@link GameSnapshot} of the just
 * started game.
 * <p>
 * Unless otherwise noted, passing a {@code null} argument to a constructor  or method in this *
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class GameStarter {

  private GameAI gameAI;

  private GameStarter(GameAI gameAI) {
    this.gameAI = gameAI;
  }

  /**
   * This methods used to start a new game with the specified {@link GameAI}
   *
   * @param gameAI represents {@link GameAI} class that should be started
   * @return the GameStarter object that can be used to play a game.
   */
  public static GameStarter startNewGame(GameAI gameAI) {
    return new GameStarter(gameAI);
  }

  /**
   * Takes {@link String} line as an input command, checks it against validity and returns {@link
   * GameSnapshot} representation of current game situation and corresponding message.
   *
   * @param inputCommand {@link String} argument representing {@link boardgame.items.boardcell.Board}
   *                     coordinates in the format : 'Letter''Number' separated by either of the
   *                     following delimiters [ -\./|] in case the figure should move several times
   *                     in one turn, move coordinates should be specified in the sequential order
   * @return {@link GameSnapshot} representation of the current game status
   * @throws IllegalArgumentException if param does not match the specified game board size
   */
  public GameSnapshot play(String inputCommand) {
    GameSnapshot gameSnapshot;
    try {
      gameAI.executeCommand(inputCommand.toUpperCase());
      gameAI.switchPlayer();
      gameSnapshot = GameSnapshot.buildSuccessCommandSnap(this.gameAI);
    } catch (NullPointerException | IllegalArgumentException exception) {
      gameSnapshot = GameSnapshot.buildErrorSnap(exception.getMessage());
    }
    return gameSnapshot;
  }

  /**
   * This method is used to get {@link GameSnapshot} representation of just stated game.
   *
   * @return {@link GameSnapshot}
   */
  public GameSnapshot getStartedGameSnap() {
    return GameSnapshot.buildJustStartedGameSnap(gameAI);
  }
}
