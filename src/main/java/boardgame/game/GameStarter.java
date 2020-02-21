package boardgame.game;

public class GameStarter {

  private GameAI gameAI;

  private GameStarter(GameAI gameAI) {
    this.gameAI = gameAI;
  }

  public static GameStarter startNewGame(GameAI gameAI) {
    return new GameStarter(gameAI);
  }

  public GameSnapshot play(String inputCommand) {
    GameSnapshot gameSnapshot;
    try {
      gameAI.executeCommand(inputCommand.toUpperCase());
      gameAI.switchPlayer();
      gameSnapshot = GameSnapshot.buildSuccessCommandSnap(this.gameAI);
    } catch (NullPointerException | IllegalArgumentException exception) {
      gameSnapshot = GameSnapshot.buildErrorSnap(exception.getMessage());
    }
    return gameSnapshot;
  }

  public GameSnapshot getStartedGameSnap() {
    return GameSnapshot.buildJustStartedGameSnap(gameAI);
  }
}
