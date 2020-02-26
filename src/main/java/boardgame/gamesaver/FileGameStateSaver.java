package boardgame.gamesaver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete {@link GameStateSaver} class that is used to save and load saves from a OS files. Class
 * implements abstract methods specified in an abstract parent class.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 */
public class FileGameStateSaver extends GameStateSaver {

  private Path filePath;
  private String gameName;

  public FileGameStateSaver(Path filePath, String gameName) {
    this.filePath = filePath;
    this.gameName = gameName;
  }

  public FileGameStateSaver(Path filePath) {
    this.filePath = filePath;
  }

  /**
   * Method is used to save a new successful command in file that contains current log. Beside
   * String parameter TimeStamp is added.
   *
   * @param successfulCommand represents String that is to be added to a log
   */
  @Override
  public void save(String successfulCommand) {
    try (BufferedWriter bufferedWriter =
        new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
      LocalDateTime localDateTime = LocalDateTime.now();
      bufferedWriter.write(localDateTime.toString());
      bufferedWriter.write(" ");
      bufferedWriter.write(successfulCommand);
      bufferedWriter.write("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that is used to get {@link GameSave} object from the initialized in a constructor file.
   *
   * @return {@link GameSave} object that can be used to reestablish game state.
   */
  @Override
  public GameSave getSave() {
    GameSave gameSave;
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
      String gameID = reader.readLine();
      String gameName = reader.readLine();
      Map<String, String> commandsList =
          Files.lines(filePath)
              .skip(2)
              .collect(
                  Collectors.toMap(line -> line.split(" ", 2)[0], line -> line.split(" ", 2)[1]));

      if (gameID == null || gameName == null) {
        throw new IllegalArgumentException("File that your are trying to load is damaged");
      }
      gameSave = new GameSave(gameID, gameName, commandsList);
    } catch (IOException e) {
      throw new IllegalArgumentException("No such file specified");
    }
    return gameSave;
  }

  /**
   * Method that is used to initialize a file with standard save, which contains unique identifier
   * and a game name that will be used to identify which game save file belongs to.
   */
  @Override
  public void createSave() {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toFile()))) {
      String[] fileNames = filePath.toString().split("_");
      bufferedWriter.write(fileNames[1]);
      bufferedWriter.write("\n");
      bufferedWriter.write(fileNames[0]);
      bufferedWriter.write("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method is used to find if save is present.
   *
   * @return true in case file exists, false - in case does not.
   */
  @Override
  public boolean hasSave() {
    return filePath.toFile().exists();
  }
}
