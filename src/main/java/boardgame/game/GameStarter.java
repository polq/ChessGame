package boardgame.game;

import boardgame.gamesaver.GameStateSaver;
import boardgame.gamesaver.GameSave;

import java.util.Map;

/**
 * The class represents main class to start working with the board games. It has a private
 * constructor and static method to start a new game. Additionally it has methods to play a game
 * that returns {@link GameSnapshot} and a method that returns {@link GameSnapshot} of the just
 * started game.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor or method in this *
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class GameStarter {

  private GameAI gameAI;
  private GameStateSaver saver;
  private boolean isNewGame;

  private GameStarter() {}

  /**
   * Takes {@link String} line as an input command, checks it against validity and returns {@link
   * GameSnapshot} representation of current game situation and corresponding message.
   *
   * @param inputCommand {@link String} argument representing {@link boardgame.items.board.Board}
   *     coordinates in the format : 'Letter''Number' separated by either of the following
   *     delimiters [ -\./|] in case the figure should move several times in one turn, move
   *     coordinates should be specified in the sequential order
   * @return {@link GameSnapshot} representation of the current game status
   * @throws IllegalArgumentException if param does not match the specified game board size
   */
  public GameSnapshot play(String inputCommand) {
    GameSnapshot gameSnapshot;
    try {
      gameAI.executeCommand(inputCommand.toUpperCase());
      gameAI.switchPlayer();
      gameSnapshot =
          new GameSnapshot.Builder()
              .withBoard(gameAI.getGameBoard())
              .withGameMessage(gameAI.getGameStatus())
              .build();
      saver.save(inputCommand);
    } catch (NullPointerException | IllegalArgumentException exception) {
      gameSnapshot = new GameSnapshot.Builder().withGameMessage(exception.getMessage()).build();
    }
    return gameSnapshot;
  }

  /**
   * This method is used to get {@link GameSnapshot} representation of game that has been started in
   * the current session. It can be loaded from a save, in case the save is damaged, the program
   * ends.
   *
   * @return {@link GameSnapshot} either of just started game, if the game has just started or
   *     loaded game if save is present.
   */
  public GameSnapshot getStartedGameSnap() {
    if (saver.hasSave() && !isNewGame) {
      try {
        GameSave gameSave = saver.getSave();
        gameSave.getCommandsLog().entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(
                entry -> {
                  this.gameAI.executeCommand(entry.getValue().toUpperCase());
                  this.gameAI.switchPlayer();
                });

        return new GameSnapshot.Builder()
            .withBoard(this.gameAI.getGameBoard())
            .withGameMessage(this.gameAI.getGameStatus())
            .build();
      } catch (IllegalArgumentException | NullPointerException e) {
        return new GameSnapshot.Builder()
            .withGameMessage(
                "It's seems the game save has been damaged. The game cannot be, please double-check the"
                    + " specified save and try again")
            .end()
            .build();
      }
    } else {
      saver.createSave();
      return new GameSnapshot.Builder()
          .withBoard(this.gameAI.getGameBoard())
          .withGameMessage(
              "Game has been started, it's "
                  + this.gameAI.getCurrentTurnPlayer()
                  + " turn. \nPlease type in cell coordinates that identify figure that you want to move and position "
                  + "where the figure should be move, separated either by space or following symbols: '-', '/', '|', '\\'")
          .build();
    }
  }

  public static class Builder {
    private GameAI gameAI;
    private GameStateSaver saver;
    private boolean isNewGame;

    public Builder withGameAI(GameAI gameAI) {
      this.gameAI = gameAI;
      return this;
    }

    public Builder withGameSaver(GameStateSaver saver) {
      this.saver = saver;
      return this;
    }

    public Builder newGame(boolean isNewGame) {
      this.isNewGame = isNewGame;
      return this;
    }

    public GameStarter build() {
      GameStarter starter = new GameStarter();
      starter.gameAI = this.gameAI;
      starter.saver = this.saver;
      starter.isNewGame = this.isNewGame;
      return starter;
    }
  }
}
