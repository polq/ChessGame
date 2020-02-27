package client;

import boardgame.game.*;
import boardgame.gamesaver.FileGameStateSaver;
import boardgame.gamesaver.GameStateSaver;
import boardgame.player.Player;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ClientController {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println(
        "In order to play, please type in game name [chess|checkers]. It will load the latest save or start a new game if no save found."
            + "\nTo start a new game explicitly, add [-new] flag."
            + "\nTo load game from a file, add [-load] flag followed by a file name separated by space."
            + "\nTo specify player names, add [-players] flag followed by names you would like to use separated by space");
    String commandString;
    GameStarter gameStarter = null;
    while (gameStarter == null) {
      try {
        commandString = scanner.nextLine();
        Args arguments = parseInputLine(commandString);
        gameStarter = getGameStarterForFile(arguments);
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
   * Method is used to create {@link GameAI} by game name, with the specified players list.
   *
   * @param gameName represents game that will be created
   * @param playerList represents list of player names.
   * @return {@link GameAI} for the specified in the param game and players list. In case {@code
   *     playerList} is null, it will return {@link GameAI} with the standard player list
   * @throws IllegalArgumentException in case there is no game matches the specified game in the
   *     param.
   */
  static GameAI getGameAI(String gameName, List<String> playerList) {
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
   * Method that is used to get {@link GameStarter} by already parsed argsObject. Input line format
   * specified in a {@link Args} class.
   *
   * @param argsObj represents {@link Args} object that contains field required to create returned
   *     object
   * @return {@link GameStarter} object that is ready to be executed.
   * @throws IllegalArgumentException in case save specified is not present or damaged.
   */
  static GameStarter getGameStarterForFile(Args argsObj) {
    GameStarter gameStarter;
    GameAI gameAI;
    GameStateSaver saver;
    boolean newGame;
    String gameName = argsObj.gameName;
    String loadFile = argsObj.gameFile;
    newGame = argsObj.newGame;
    List<String> players = argsObj.players;
    gameAI = getGameAI(gameName, players);
    if (loadFile != null) {
      saver = FileGameStateSaver.getGameStateFromAFile(loadFile, gameName);
    } else {
      Optional<String> latestSave = FileGameStateSaver.findMostRecentFileSave(gameName);
      if (latestSave.isPresent() && !newGame) {
        saver = new FileGameStateSaver(Path.of(latestSave.get()), gameName);
      } else {
        LocalDateTime dateTime = LocalDateTime.now();
        String fileName = gameName + "_" + dateTime.toString();
        saver = new FileGameStateSaver(Path.of(fileName), gameName);
        newGame = true;
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

    @Parameter(names = "-load", description = "Specifies file to load game")
    public String gameFile;
  }
}
