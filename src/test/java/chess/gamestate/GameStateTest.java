package chess.gamestate;

import chess.items.board.Board;
import chess.items.board.Cell;
import chess.items.board.QueenableCell;
import chess.items.chesspieces.ChessFigure;
import chess.items.chesspieces.bishop.Bishop;
import chess.items.chesspieces.king.King;
import chess.items.chesspieces.knight.Knight;
import chess.items.chesspieces.pawn.Pawn;
import chess.items.chesspieces.queen.Queen;
import chess.items.chesspieces.rook.Rook;
import chess.player.BlackPlayer;
import chess.player.ChessPlayer;
import chess.player.WhitePlayer;
import chess.rules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

  GameState state;
  Board board;
  Cell startPoint;
  Cell endPoint;
  WhitePlayer whitePlayer;
  BlackPlayer blackPlayer;

  @BeforeEach
  public void init() {
    board = new Board(new ImaginaryGameRule());
    state = new ActiveGameState(board);
    whitePlayer = new WhitePlayer(1);
    blackPlayer = new BlackPlayer(-1);
  }

  @Test
  void testSwitchPlayer() {
    ChessPlayer initialPlayer = state.getCurrentTurnPlayer();
    state.switchPlayer();
    ChessPlayer playerAfterSwitch = state.getCurrentTurnPlayer();

    assertNotEquals(initialPlayer.getClass(), playerAfterSwitch.getClass());
  }

  @Test
  void testGetCurrentTurnPlayer() {
    assertEquals(whitePlayer.getClass(), state.getCurrentTurnPlayer().getClass());
  }

  @Test
  void testCheckOwner() {
    King blackKing = new King(blackPlayer, "");
    King whiteKing = new King(whitePlayer, "");

    assertFalse(state.checkOwner(blackKing));
    assertTrue(state.checkOwner(whiteKing));
  }

  @Test
  void testExecuteMove() {
    Cell fromCell = new Cell('A', 1);
    fromCell.setFigure(new Pawn(whitePlayer, ""));
    Cell toCell = new Cell('A', 2);
    toCell.setEmpty(true);

    state.executeMove(fromCell, toCell);

    assertTrue(fromCell.isEmpty());
    assertTrue(toCell.getFigure() != null);
    assertEquals(Pawn.class, toCell.getFigure().getClass());
  }

  @Test
  void testSwitchFigure() {
    ChessFigure figure = new Pawn(whitePlayer, "");
    Cell fromCell = new Cell('A', 1);
    fromCell.setFigure(figure);
    Cell toCell = new Cell('A', 2);
    toCell.setEmpty(true);

    state.switchFigure(fromCell, toCell, figure);

    assertTrue(fromCell.isEmpty());
    assertFalse(toCell.isEmpty());
    assertTrue(figure.isMoved());
  }

  @Test
  void testBecomeQueen() {
    ChessFigure pawnFigure = new Pawn(whitePlayer, "");
    Cell queenableCell = new QueenableCell('A', 1);
    Cell notQueenableCell = new Cell('A', 2);
    notQueenableCell.setEmpty(true);
    queenableCell.setEmpty(true);

    state.becomeQueen(pawnFigure, queenableCell);
    state.becomeQueen(pawnFigure, notQueenableCell);

    assertEquals(Queen.class, queenableCell.getFigure().getClass());
    assertTrue(notQueenableCell.isEmpty());
  }

  @Test
  void testExecuteBeat() {
    ChessFigure figureToBeat = new Queen(whitePlayer, "");
    ChessFigure figureToBeBeaten = new Pawn(blackPlayer, "");
    Cell fromCell = new Cell('A', 1);
    Cell toCell = new Cell('A', 2);
    fromCell.setFigure(figureToBeat);
    toCell.setFigure(figureToBeBeaten);

    state.executeBeat(fromCell, toCell);
    assertEquals(toCell.getFigure().getClass(), figureToBeat.getClass());
    assertTrue(fromCell.isEmpty());
  }

  @Test
  void testExecuteCastle() {
    ChessFigure kingFigure = new King(whitePlayer, "");
    ChessFigure rookFigure = new Rook(whitePlayer, "");
    Cell initialKingCell = new Cell('B', 1);
    Cell initialRookCell = new Cell('B', 4);
    Cell anotherRookCell = new Cell('B', 2);
    initialKingCell.setFigure(kingFigure);
    initialRookCell.setFigure(rookFigure);
    anotherRookCell.setFigure(rookFigure);

    state.executeCastle(initialKingCell, initialRookCell);

    assertEquals(initialRookCell.getFigure().getClass(), kingFigure.getClass());
    assertEquals(initialKingCell.getFigure().getClass(), rookFigure.getClass());
    assertThrows(
        IllegalArgumentException.class,
        () -> state.executeCastle(initialKingCell, anotherRookCell));
  }

  @Test
  void testGetAllAliveFigures() {
    Board standardRulesBoard = new Board(new StandardChessRule());
    Board imaginaryRulesBoard = new Board(new ImaginaryGameRule());
    GameState standardState = new ActiveGameState(standardRulesBoard);
    GameState imaginaryState = new ActiveGameState(imaginaryRulesBoard);

    assertEquals(4, imaginaryState.getAliveFigures(blackPlayer).size());
    assertEquals(16, standardState.getAliveFigures(whitePlayer).size());
  }

  @Test
  void testFindKing() {
    Cell kingCell = state.findKing(whitePlayer);
    assertEquals(King.class, kingCell.getFigure().getClass());
    assertEquals(WhitePlayer.class, kingCell.getFigure().getChessOwner().getClass());
  }

  @Test
  void testIsPathClear() {
    startPoint = new Cell('A', 1);
    endPoint = new Cell('b', 3);
    ChessFigure knight = new Knight(blackPlayer, "");
    ChessFigure bishop = new Bishop(blackPlayer, "");

    assertTrue(state.isPathClear(startPoint, endPoint, knight));
    assertFalse(state.isPathClear(startPoint, endPoint, bishop));
    assertThrows(
        IllegalArgumentException.class, () -> state.isPathClear(endPoint, endPoint, knight));
    assertThrows(NullPointerException.class, () -> state.isPathClear(null, endPoint, knight));
  }

  @Test
  void testIsUnderCheck() {
    Cell normalRuleKingCell = state.findKing(whitePlayer);

    GameRule underCheckRule = new CheckGameRule();
    Board checkBoard = new Board(underCheckRule);
    GameState checkState = new ActiveGameState(checkBoard);
    Cell kingCellOnTestBoard = checkState.findKing(whitePlayer);

    assertTrue(checkState.isUnderCheck(whitePlayer, kingCellOnTestBoard));
    assertFalse(state.isUnderCheck(whitePlayer, normalRuleKingCell));
  }

  @Test
  void testIsUnderCheckMate() {
    Cell normalKingCell = state.findKing(blackPlayer);
    // * Testing board
    // *   A B C
    // * 2 ♔ □ ♛
    // * 1 □ □ ♛
    GameRule testRule = new CheckMateGameRule();
    GameState testingGameState = new ActiveGameState(new Board(testRule));
    Cell testingKingCell = state.findKing(whitePlayer);

    assertFalse(state.isUnderCheckMate(blackPlayer, normalKingCell));
    assertTrue(testingGameState.isUnderCheckMate(whitePlayer, testingKingCell));
  }

  @Test
  void testSwitchGameState() {
    GameState normalGameState = new ActiveGameState(new Board(new StandardChessRule()));
    GameState checkGameState = new ActiveGameState(new Board(new CheckGameRule()));
    GameState checkMateGameState = new ActiveGameState(new Board(new CheckMateGameRule()));
    GameState drawGameState = new ActiveGameState(new Board(new DrawGameRule()));

    drawGameState = drawGameState.switchGameState();
    normalGameState = normalGameState.switchGameState();
    checkGameState = checkGameState.switchGameState();
    checkMateGameState = checkMateGameState.switchGameState();

    assertEquals(ActiveGameState.class, normalGameState.getClass());
    assertEquals(CheckGameState.class, checkGameState.getClass());
    assertEquals(CheckMateGameState.class, checkMateGameState.getClass());
    assertEquals(DrawGameState.class, drawGameState.getClass());
  }
}
