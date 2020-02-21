package client;

import boardgame.game.*;

import java.util.Scanner;

public class ClientController {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
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
    GameStarter gameStarter = GameStarter.startNewGame(gameAI);
    GameSnapshot gameSnapshot = gameStarter.getStartedGameSnap();
    System.out.println(gameSnapshot.getStringGameSnap());
    String inputCommand;

    do {
      inputCommand = scanner.nextLine();
      gameSnapshot = gameStarter.play(inputCommand);
      System.out.println(gameSnapshot.getStringGameSnap());
    } while (gameSnapshot.isActive());
  }
}
