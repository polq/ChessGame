package client;

import boardgame.game.*;
import boardgame.gamesaver.FileGameStateSaver;
import boardgame.gamesaver.GameStateSaver;
import boardgame.gamesaver.GameSave;
import boardgame.player.Player;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientController {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome message");
    String commandString;
    Args argsObj = new Args();
    GameAI gameAI;
    GameStateSaver saver;
    boolean newGame;
    while (true) {
      try {
        commandString = scanner.nextLine();
        JCommander.newBuilder().addObject(argsObj).build().parse(commandString.split(" "));
        String gameName = argsObj.gameName;
        String loadFile = argsObj.gameFile;
        newGame = argsObj.newGame;
        List<String> players = argsObj.players;
        gameAI = getGameAI(gameName, players);
        if (loadFile != null) {
          saver = getGameStateFromAFile(loadFile, gameName);
        } else {
          Optional<String> latestSave = findMostRecentFileSave(gameName);
          if (latestSave.isPresent()) {
            saver = new FileGameStateSaver(Path.of(latestSave.get()), gameName);
          } else {
            LocalDateTime dateTime = LocalDateTime.now();
            String fileName = gameName + "_" + dateTime.toString();
            saver = new FileGameStateSaver(Path.of(fileName), gameName);
            newGame = true;
          }
        }
        break;
      } catch (ParameterException | IllegalArgumentException e) {
        System.out.println(e.getMessage());
      }
    }

    GameStarter gameStarter =
        new GameStarter.Builder().withGameAI(gameAI).withGameSaver(saver).newGame(newGame).build();
    GameSnapshot gameSnapshot = gameStarter.getStartedGameSnap();
    System.out.println(gameSnapshot.getStringGameSnap());
    String inputCommand;

    while (gameSnapshot.isActive()) {
      inputCommand = scanner.nextLine();
      gameSnapshot = gameStarter.play(inputCommand);
      System.out.println(gameSnapshot.getStringGameSnap());
    }
  }

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

  static GameStateSaver getGameStateFromAFile(String fileName, String gameName) {
    GameStateSaver newFileGameSaver = new FileGameStateSaver(Path.of(fileName));
    GameSave save = newFileGameSaver.getSave();
    if (save.getGameName().equals(gameName)) {
      return newFileGameSaver;
    } else {
      throw new IllegalArgumentException("No such file specified in a load parameter exists");
    }
  }

  static Optional<String> findMostRecentFileSave(String gameName) {
    Path path = Path.of(".");
    return Stream.of(Objects.requireNonNull(path.toFile().list()))
        .filter(line -> line.startsWith(gameName + "_"))
        .sorted()
        .findFirst();
  }

  static class Args {

    @Parameter(description = "main parameter that defines game to play", required = true)
    private String gameName;

    @Parameter(
        names = "-players",
        arity = 2,
        description = "Space-separated list of player names to be run")
    private List<String> players;

    @Parameter(names = "-new", description = "Boolean flag to explicitly start new game")
    private boolean newGame;

    @Parameter(names = "-load", description = "Specifies file to load game")
    private String gameFile;
  }
}
