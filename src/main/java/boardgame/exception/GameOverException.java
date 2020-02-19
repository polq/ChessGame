package boardgame.exception;

/**
 * Thrown when a {@link boardgame.game.Game} has ended in the following ways:
 *
 * <ul>
 *   <il> Game resulted in one player losing </il>
 *   <il> Game resulted in a draw </il>
 * </ul>
 *
 * Application should throw this exception when no moves are to be executed.
 *
 * <p>{@link GameOverException} should be constructed with the {@link String} message identifying
 * the exact result of the game.
 */
public class GameOverException extends RuntimeException {

  public GameOverException(String message) {
    super(message);
  }
}
