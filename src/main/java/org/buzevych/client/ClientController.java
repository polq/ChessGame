package org.buzevych.client;

import org.buzevych.boardgame.game.*;
import org.buzevych.boardgame.gamesaver.FileGameStateSaver;
import org.buzevych.boardgame.gamesaver.GameStateSaver;
import org.buzevych.boardgame.gamesaver.JDBCGameStateSaver;
import org.buzevych.boardgame.player.Player;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ClientController {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "In order to play, please type in game name [chess|checkers]. It will load the latest save or start a new game if no save found."
            + "\nTo start a new game explicitly, add [-new] flag."
            + "\nTo load game from a file, add [-load] flag followed by a file name separated by space."
            + "\nTo specify player names, add [-players] flag followed by names you would like to use separated by space"
            + "\nTo use database as saves storage, used [-db] flag");
    String commandString;
    GameStarter gameStarter = null;
    while (gameStarter == null) {
      try {
        commandString = scanner.nextLine();
        Args arguments = parseInputLine(commandString);
        if (arguments.loadDB) {
          gameStarter = DBStarter(arguments);
        } else {
          gameStarter = fileStarter(arguments);
        }
      } catch (ParameterException | IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }
    GameSnapshot gameSnapshot = gameStarter.getStartedGameSnap();
    System.out.println(gameSnapshot.getStringGameSnap());
    String inputCommand;

    while (gameSnapshot.isActive()) {
      inputCommand = scanner.nextLine();
      gameSnapshot = gameStarter.play(inputCommand);
      System.out.println(gameSnapshot.getStringGameSnap());
    }
  }

  /**
   * Method that is used to get {@link GameStarter} for a DataBase by already parsed argsObject.
   * Input line format specified in a {@link Args} class.
   *
   * @param argsObj represents {@link Args} object that contains field required to create returned
   *     object
   * @return {@link GameStarter} object that is ready to be executed.
   */
  static GameStarter DBStarter(Args argsObj) {
    GameAI gameAI;
    boolean newGame;
    String gameName = argsObj.gameName;
    newGame = argsObj.newGame;
    List<String> players = argsObj.players;
    gameAI = nameGameAI(gameName, players);
    GameStateSaver saver = new JDBCGameStateSaver(gameName);
    if (!newGame) {
      saver = saver.latestSave();
    }
    return new GameStarter.Builder()
        .withGameSaver(saver)
        .withGameAI(gameAI)
        .newGame(newGame)
        .build();
  }

  /**
   * Method is used to create {@link GameAI} by game name, with the specified players list.
   *
   * @param gameName represents game that will be created
   * @param playerList represents list of player names.
   * @return {@link GameAI} for the specified in the param game and players list. In case {@code
   *     playerList} is null, it will return {@link GameAI} with the standard player list
   * @throws IllegalArgumentException in case there is no game matches the specified game in the
   *     param.
   */
  static GameAI nameGameAI(String gameName, List<String> playerList) {
    GameAI gameAI;
    LinkedList<Player> players = null;
    if (playerList != null) {
      players =
          playerList.stream().map(Player::new).collect(Collectors.toCollection(LinkedList::new));
    }
    if (gameName.equals("chess")) {
      gameAI = playerList == null ? new ChessGameAI() : new ChessGameAI(players);
    } else if (gameName.equals("checkers")) {
      gameAI = playerList == null ? new CheckersGameAI() : new ChessGameAI(players);
    } else {
      throw new IllegalArgumentException(
          "Incorrect game name, please check your input and try again");
    }
    return gameAI;
  }

  /**
   * Method that is used to get {@link GameStarter} for a File by already parsed argsObject. Input
   * line format specified in a {@link Args} class.
   *
   * @param argsObj represents {@link Args} object that contains field required to create returned
   *     object
   * @return {@link GameStarter} object that is ready to be executed.
   * @throws IllegalArgumentException in case save specified is not present or damaged.
   */
  static GameStarter fileStarter(Args argsObj) {
    GameStarter gameStarter;
    GameAI gameAI;
    GameStateSaver saver;
    boolean newGame;
    String gameName = argsObj.gameName;
    String loadFile = argsObj.gameFile;
    newGame = argsObj.newGame;
    List<String> players = argsObj.players;
    gameAI = nameGameAI(gameName, players);
    if (loadFile != null) {
      saver = new FileGameStateSaver(Path.of(loadFile), gameName);
    } else {
      saver = new FileGameStateSaver(gameName);
      if (!newGame) {
        saver = saver.latestSave();
      }
    }
    gameStarter =
        new GameStarter.Builder().withGameAI(gameAI).withGameSaver(saver).newGame(newGame).build();

    return gameStarter;
  }

  /**
   * Method that is used to parse input line into and convenient object format.
   *
   * @param inputLine String that should be parsed
   * @return {@link Args} object that contains field parsed from input param
   * @throws ParameterException in case the inputLine does not match the required param format.
   */
  static Args parseInputLine(String inputLine) {
    Args args = new Args();
    JCommander.newBuilder().addObject(args).build().parse(inputLine.split(" "));
    return args;
  }

  static class Args {

    @Parameter(description = "main parameter that defines game to play", required = true)
    public String gameName;

    @Parameter(
        names = "-players",
        arity = 2,
        description = "Space-separated list of player names to be run")
    public List<String> players;

    @Parameter(names = "-new", description = "Boolean flag to explicitly start new game")
    public boolean newGame;

    @Parameter(names = "-load", description = "Specifies file to load game from")
    public String gameFile;

    @Parameter(
        names = "-db",
        description = "Flag that specifies that DB should be used, file save is default value")
    public boolean loadDB;
  }
}
