package boardgame.gamesaver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Concrete {@link GameStateSaver} class that is used to save and load saves from a OS files. Class
 * implements abstract methods specified in an abstract parent class.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 *
 * <p>Standard save file has the following format:
 *
 * <p>2020-02-27T11:35:39.275733 [Unique ID - timeStamp]<br>
 * chess [Game name]<br>
 * 2020-02-27T11:35:41.835101 e2 e4 [Command with the timeStamp]<br>
 * 2020-02-27T11:35:49.552936 a7 a5 [Command with the timeStamp]<br>
 * 2020-02-27T11:36:39.578808 f1 a6 [Command with the timeStamp]
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
      LocalDateTime dateTime = LocalDateTime.now();
      bufferedWriter.write(dateTime.toString());
      bufferedWriter.write("\n");
      bufferedWriter.write(this.gameName);
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

  /**
   * Util method that used to get GameSave from a file with a specified game name;
   *
   * @param fileName represents a file name that contains a save
   * @param gameName represents game name that save belongs to.
   * @return in case save exist and belongs to the same game name, specified in the params, game
   *     stat saver object is returned.
   * @throws IllegalArgumentException in case game specified in param does not match with the game
   *     listed in a file
   */
  public static GameStateSaver getGameStateFromAFile(String fileName, String gameName) {
    GameStateSaver newFileGameSaver = new FileGameStateSaver(Path.of(fileName), gameName);
    GameSave save = newFileGameSaver.getSave();
    if (save.getGameName().equals(gameName)) {
      return newFileGameSaver;
    } else {
      throw new IllegalArgumentException(
          "Game specified in file does not match with the selected one");
    }
  }

  /**
   * Until method that is used to get the most recent save in the current directory by the game name
   *
   * @param gameName represents game, that save will be looked fro
   * @return {@link Optional} that contains the most recent save file name in case there is any.
   */
  public static Optional<String> findMostRecentFileSave(String gameName) {
    Path path = Path.of(".");
    return Stream.of(Objects.requireNonNull(path.toFile().list()))
        .filter(line -> line.startsWith(gameName + "_"))
        .max(Comparator.naturalOrder());
  }
}
