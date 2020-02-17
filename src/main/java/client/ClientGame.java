package client;

import chess.exception.GameOverException;
import chess.game.CheckersGame;
import chess.game.ChessGame;
import chess.game.Game;
import chess.rules.GameRule;
import chess.rules.RussianCheckersRule;
import chess.rules.StandardChessRule;

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
