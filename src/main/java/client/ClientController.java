package client;

import boardgame.game.*;
import boardgame.gamesaver.FileGameStateSaver;
import boardgame.gamesaver.GameStateSaver;
import boardgame.gamesaver.GameSave;

import java.nio.file.Path;
import java.util.Scanner;

public class ClientController {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    GameStarter gameStarter = selectGame(scanner);
    GameSnapshot gameSnapshot = gameStarter.getStartedGameSnap();
    System.out.println(gameSnapshot.getStringGameSnap());
    String inputCommand;

    while (gameSnapshot.isActive()) {
      inputCommand = scanner.nextLine();
      gameSnapshot = gameStarter.play(inputCommand);
      System.out.println(gameSnapshot.getStringGameSnap());
    }
  }

  static GameStarter selectGame(Scanner scanner) {
    GameAI gameAI;
    System.out.println("What game would you like to play? [chess|checkers]");
    while (true) {
      String inputGame = scanner.nextLine();
      if (inputGame.equals("chess")) {
        gameAI = new ChessGameAI();
        break;
      } else if (inputGame.equals("checkers")) {
        gameAI = new CheckersGameAI();
        break;
      } else {
        System.out.println(
            "Please type in correct game name, that you would like to play: [chess|checkers]");
      }
    }
    String standardStringSaveName = gameAI.getGameName() + ".txt";
    GameStateSaver saver = new FileGameStateSaver(Path.of(standardStringSaveName));
    boolean isNewGame = false;
    if (saver.hasSave()) {
      System.out.println(
          "Standard save for "
              + gameAI.getGameName()
              + " game has been found. Would you like to load it: [Y|N]"
              + "\nAlternately, in order to load a game from a another file, please write: load [fileName]");

      while (true) {
        String reply = scanner.nextLine();
        if (reply.equals("N")) {
          saver = new FileGameStateSaver(Path.of(standardStringSaveName), gameAI.getGameName());
          isNewGame = true;
        } else if (reply.equals("Y")) {
          break;
        } else if (reply.contains("load")) {
          String fileName = reply.split("load ")[1];
          GameStateSaver newFileGameSaver = new FileGameStateSaver(Path.of(fileName));
          GameSave save = newFileGameSaver.getSave();
          if (save.getGameName().equals(gameAI.getGameName())) {
            saver = newFileGameSaver;
            break;
          } else {
            System.out.println(
                "Game name in the file does not match specified game name. Start a new Game or load an existing one by typing [Y|N]");
          }
        } else {
          System.out.println(
              "Incorrect command, please specify if you would like to load current game, by replying with Y or N. Or load a game from a file by typing: load [file name] (without brackets)");
        }
      }
    } else {
      saver = new FileGameStateSaver(Path.of(standardStringSaveName), gameAI.getGameName());
      isNewGame = true;
    }

    return GameStarter.startNewGame(gameAI, saver, isNewGame);
  }
}
