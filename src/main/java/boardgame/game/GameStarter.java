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

  private GameStarter(GameAI gameAI, GameStateSaver saver, boolean isNewGame) {
    this.gameAI = gameAI;
    this.saver = saver;
    this.isNewGame = isNewGame;
  }

  /**
   * This methods used to start a new game with the specified {@link GameAI}
   *
   * @param gameAI represents {@link GameAI} class that should be started
   * @param saver represent GameStateSaver with will be used to log the game
   * @param isNewGame represents if the game is just started and should be recorded from scratch.
   * @return the GameStarter object that can be used to play a game.
   */
  public static GameStarter startNewGame(GameAI gameAI, GameStateSaver saver, boolean isNewGame) {
    return new GameStarter(gameAI, saver, isNewGame);
  }

  /**
   * Takes {@link String} line as an input command, checks it against validity and returns {@link
   * GameSnapshot} representation of current game situation and corresponding message.
   *
   * @param inputCommand {@link String} argument representing {@link
   *     boardgame.items.boardcell.Board} coordinates in the format : 'Letter''Number' separated by
   *     either of the following delimiters [ -\./|] in case the figure should move several times in
   *     one turn, move coordinates should be specified in the sequential order
   * @return {@link GameSnapshot} representation of the current game status
   * @throws IllegalArgumentException if param does not match the specified game board size
   */
  public GameSnapshot play(String inputCommand) {
    GameSnapshot gameSnapshot;
    try {
      gameAI.executeCommand(inputCommand.toUpperCase());
      gameAI.switchPlayer();
      gameSnapshot = GameSnapshot.buildSuccessCommandSnap(this.gameAI);
      saver.saveNewCommand(inputCommand);
    } catch (NullPointerException | IllegalArgumentException exception) {
      gameSnapshot = GameSnapshot.buildErrorSnap(exception.getMessage());
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

        return GameSnapshot.buildSuccessCommandSnap(this.gameAI);
      } catch (IllegalArgumentException | NullPointerException e) {
        return GameSnapshot.buildErrorSaveSnapshot();
      }
    } else {
      saver.createNewSave();
      return GameSnapshot.buildJustStartedGameSnap(gameAI);
    }
  }
}
