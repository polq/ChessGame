package client;

import chess.game.Game;
import chess.rules.GameRule;
import chess.rules.StandardChessRule;

import java.util.Scanner;

public class ClientGame {
  public static void main(String[] args) {
    GameRule desiredRule = new StandardChessRule();
    Game chessGame = new Game();
    chessGame.startNewGame(desiredRule);
    System.out.println("Game has been started\n");
    System.out.println(chessGame);

    Scanner scanner = new Scanner(System.in);
    String inputCommand;
    while ((inputCommand = scanner.nextLine()) != null) {
      try{
        System.out.println(chessGame.play(inputCommand));
      } catch (IllegalArgumentException e){
        System.out.println(e.getMessage());
      } catch (NullPointerException e){
        System.out.println(e.getMessage());
      }
    }

    scanner.close();
  }
}
