package chess.gamestate;

import chess.items.board.Board;
import chess.items.chesspieces.pawn.Pawn;
import chess.player.playertypes.WhitePlayer;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

  GameState state;

  @BeforeEach
  public void init() {
    Board gameBoard = new Board(new StandardChessRule());
    state = new NotStartedGameState(gameBoard);
  }

  @Test
  public void testNotStartedGameState() {
    state.executeCommand("E2", "E4");
    assertEquals(
        WhitePlayer.class,
        ((Pawn) (state.getGameBoard().getBoardCells().get("E4").getFigure())).getClass());

    assertEquals(
        "\u2659", state.getGameBoard().getBoardCells().get("E4").getFigure().getChessIcon());
  }

  @Test
  public void rangeTest(){

  }
}
