package chess.gamestate;

import chess.game.Game;
import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.chesspieces.ChessFigure;
import chess.items.chesspieces.bishop.Bishop;
import chess.items.chesspieces.knight.Knight;
import chess.items.chesspieces.pawn.Pawn;
import chess.player.BlackPlayer;
import chess.player.ChessPlayer;
import chess.player.WhitePlayer;
import chess.rules.ImaginaryGameRule;
import chess.rules.StandardChessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

  GameState state;
  Board board;
  ChessFigure figure;
  ChessPlayer player;
  Cell startPoint;
  Cell endPoint;

  @BeforeEach
  public void init() {
    board = new Board(new StandardChessRule());
    state = new ActiveGameState(board);
    player = new BlackPlayer();
  }

  @Test
  void testPathing() {
    startPoint = new Cell('B', 1);
    endPoint = new Cell('B', 3);

    figure = new Knight(player, "");
    assertTrue(state.isPathClear(startPoint, endPoint, figure));

    figure = new Bishop(player, "");
    assertFalse(state.isPathClear(startPoint, endPoint, figure));
  }

  @Test
  void testQueenAble() {
    endPoint = new Cell('b', 8);

    Cell toCell = board.getBoardCells().get(endPoint.getStringKey());
    figure = new Pawn(player, "");
    state.executeBecomeQueen(figure, toCell);
    assertEquals(
        player.toString() + " Queen",
        board.getBoardCells().get(endPoint.getStringKey()).getFigure().toString());
  }

  @Test
  void isUnderCheck() {
    Cell king = state.findKing(player);
    assertEquals(player + " King", king.getFigure().toString());
    assertFalse(state.isUnderCheck(player, king));

    player = new WhitePlayer();
    king = state.findKing(player);
    assertEquals(player + " King", king.getFigure().toString());
  }

  @Test
  void isUnderCheckImaginaryRule() {
    board = new Board(new ImaginaryGameRule());
    state = new ActiveGameState(board);

    Cell blackKing = state.findKing(player);
    assertEquals("B4", blackKing.getStringKey());

    assertFalse(state.isUnderCheck(player, blackKing));

    state.executeCommand("A1", "C2");
    state.executeCommand("C1", "B2");
    assertTrue(state.isUnderCheck(player, blackKing));
  }

  @Test
  void isUnderCheckMate() {
    board = new Board(new ImaginaryGameRule());
    state = new ActiveGameState(board);

    Cell blackKing = state.findKing(player);

    state.executeCommand("A1", "C2");
    assertTrue(state.isUnderCheck(player, blackKing));
    assertFalse(state.isUnderCheckMate(player, blackKing));

    state.executeCommand("C1", "B2");
    assertTrue(state.isUnderCheck(player, blackKing));
    // System.out.println(state);
    assertTrue(state.isUnderCheckMate(player, blackKing));
  }

  @Test
  void moveUnderCheck() {
    board = new Board(new ImaginaryGameRule());
    state = new ActiveGameState(board);
    assertThrows(IllegalArgumentException.class, () -> state.executeCommand("B1", "C2"));
  }

  @Test
  void testSwitch() {
    board = new Board(new ImaginaryGameRule());
    state = new ActiveGameState(board);
    Game game = new Game();
    game.startNewGame(new ImaginaryGameRule());
    System.out.println(game);
    System.out.println(game.play("A1-C2"));
  }
}
