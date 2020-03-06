package org.buzevych.boardgame.gamesaver;

import java.util.Map;

/**
 * Class that contains fields and methods that identify save. Common save instance contains of game
 * name, that identifies which game the save belongs to, unique ID to distinguish save instances and
 * Map containing commands with String TimeStamp as a key and move as a value.
 */
public class GameSave {

  private Map<String, String> commandsLog;
  private String gameName;

  public String getUniqueSaveID() {
    return uniqueSaveID;
  }

  private String uniqueSaveID;

  public GameSave(String uniqueSaveID, String gameName, Map<String, String> commandsLog) {
    this.uniqueSaveID = uniqueSaveID;
    this.commandsLog = commandsLog;
    this.gameName = gameName;
  }

  public Map<String, String> getCommandsLog() {
    return commandsLog;
  }

  public String getGameName() {
    return gameName;
  }
}
