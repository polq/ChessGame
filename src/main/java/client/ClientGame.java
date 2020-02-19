package client;

import boardgame.exception.GameOverException;
import boardgame.game.CheckersGame;
import boardgame.game.Game;
import boardgame.rules.GameRule;
import boardgame.rules.RussianCheckersRule;

import java.util.Scanner;

public class ClientGame {

  public static void main(String[] args) {
    GameRule desiredRule = new RussianCheckersRule();
    Game chessGame = new CheckersGame();
    chessGame.startNewGame(desiredRule);
    System.out.println("Game has been started, White player plays first\n");
    System.out.println(chessGame);

    Scanner scanner = new Scanner(System.in);
    String inputCommand;
    while ((inputCommand = scanner.nextLine()) != null) {
      try {
        System.out.println(chessGame.play(inputCommand));
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
      } catch (NullPointerException e) {
        System.out.println(e.getMessage());
      } catch (GameOverException e) {
        System.out.println(e.getMessage());
        break;
      }
    }

    scanner.close();
  }
}
