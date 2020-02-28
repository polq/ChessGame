package boardgame.gamesaver;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete {@link GameStateSaver} class that is used to save and load saves from Database. Class
 * implements methods specified in an abstract parent class.
 *
 * <p>Unless otherwise noted, passing a {@code null} argument to a constructor * or method in this
 * class will cause a {@link NullPointerException} to be thrown.
 *
 * <p>Database stores information in the following format:
 *     game_name,             game_id,                    move_id            move
 *      chess,     2020-02-28T16:51:19.122983, 2020-02-28T16:54:53.950757, d8 e8
 *      chess,     2020-02-28T16:51:19.122983, 2020-02-28T16:53:56.748506, d7 e8
 *      chess,     2020-02-28T16:51:19.122983, 2020-02-28T16:53:44.812731, e7 e6
 *      chess,     2020-02-28T16:51:19.122983, 2020-02-28T16:53:31.247281, e6 d7
 *      chess,     2020-02-28T16:51:19.122983, 2020-02-28T16:53:09.482712, b8 c6
 */

public class JDBCGameStateSaver extends GameStateSaver {

  private String gameName;
  private String gameID;
  private DataSource dataSource;

  public JDBCGameStateSaver(String gameName) {
    this(gameName, JDBCDataSource.getMySQLDataSource());
  }

  JDBCGameStateSaver(String gameName, DataSource dataSource) {
    this.gameName = gameName;
    this.dataSource = dataSource;
  }

  private JDBCGameStateSaver(String gameName, String gameID) {
    this(gameName, JDBCDataSource.getMySQLDataSource());
    this.gameID = gameID;
  }

  /**
   * Method is used to save a new successful command into a database. Successful move in database is
   * represented by a game_id, move_id (time stamp) and a move.
   *
   * @param successfulCommand represents String that is to be added to a log
   */
  @Override
  public void save(String successfulCommand) {
    try (Connection connection = this.dataSource.getConnection()) {
      LocalDateTime dateTime = LocalDateTime.now();
      String moveID = dateTime.toString();
      PreparedStatement insertStatement =
          connection.prepareStatement("INSERT into game_moves values(?, ?, ?);");
      insertStatement.setString(1, this.gameID);
      insertStatement.setString(2, moveID);
      insertStatement.setString(3, successfulCommand);
      insertStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that is used to get {@link GameSave} object from the database with the current game id.
   *
   * @return {@link GameSave} object that can be used to reestablish game state.
   */
  @Override
  public GameSave getSave() {
    try (Connection connection = this.dataSource.getConnection()) {
      PreparedStatement selectStatement =
          connection.prepareStatement("SELECT move_id, move FROM game_moves where game_id = ?;");
      selectStatement.setString(1, this.gameID);
      ResultSet resultSet = selectStatement.executeQuery();
      Map<String, String> commandsMap = new HashMap<>();
      while (resultSet.next()) {
        commandsMap.put(resultSet.getString(1), resultSet.getString(2));
      }
      return new GameSave(gameID, gameName, commandsMap);
    } catch (SQLException e) {
      throw new IllegalArgumentException("Error while getting save");
    }
  }

  /**
   * Method that is used to initialize a new game with standard save, which contains unique
   * identifier and a game name that will be used to identify which game save belongs to.
   */
  @Override
  public void createSave() {
    try (Connection connection = this.dataSource.getConnection()) {
      LocalDateTime dateTime = LocalDateTime.now();
      String gameID = dateTime.toString();
      PreparedStatement insertStatement =
          connection.prepareStatement("INSERT into games values (?, ?);");
      insertStatement.setString(1, gameID);
      insertStatement.setString(2, this.gameName);
      insertStatement.executeUpdate();
      this.gameID = gameID;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method that is used to get the last instance of a game saver from a database for a specific
   * game name, specified in a param.
   *
   * @param gameName represents the name for which the save will be searched and returned.
   * @return {@link GameStateSaver} for the latest save for the specified game or in case no save
   *     found, it will create a new one.
   */
  public static JDBCGameStateSaver getLatestSaveOrNew(String gameName) {
    DataSource dataSource = JDBCDataSource.getMySQLDataSource();
    try (Connection connection = dataSource.getConnection()) {
      PreparedStatement statement =
          connection.prepareStatement("SELECT max(game_id) FROM games Where game_name = ? ;");
      statement.setString(1, gameName);
      ResultSet resultSet = statement.executeQuery();
      String gameID = null;
      while (resultSet.next()) {
        gameID = resultSet.getString(1);
      }
      if (gameID == null) {
        JDBCGameStateSaver saver = new JDBCGameStateSaver(gameName);
        saver.createSave();
        return saver;
      }
      return new JDBCGameStateSaver(gameName, gameID);
    } catch (SQLException e) {
      throw new IllegalArgumentException("Fail while saving message, " + e.getMessage());
    }
  }

  String getGameID() {
    return gameID;
  }
}
