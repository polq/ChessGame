package boardgame.gamesaver;

/**
 * Class that is used to log game and to load board game from a specified game saver.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public abstract class GameStateSaver {

  /**
   * Method that is used to add new successfully executed command to a log file.
   *
   * @param successfulCommand represents String that is to be added to a log
   */
  public abstract void saveNewCommand(String successfulCommand);

  /**
   * This method is used to retrieve {@link GameSave} object that contains information on how to get
   * to a saved state of the game.
   *
   * @return {@link GameSave} that represents game save
   */
  public abstract GameSave getSave();

  /** Method that is used to create a new save instance */
  public abstract void createNewSave();

  /**
   * Method is used to check if current save is present
   *
   * @return true in case save is present, false - in case there is no save
   */
  public abstract boolean hasSave();
}
