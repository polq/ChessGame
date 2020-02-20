package boardgame.gamestate;

import boardgame.items.board.ChessBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessGameStateTest {

  ChessGameState gameState;

  @BeforeEach
  void init() {
    gameState = new ChessGameState(new ChessBoardFactory().createBoard());
  }

  @Test
  void getGameStatusActive() {
    assertEquals("It's " + gameState.getCurrentTurnPlayer() + " turn.", gameState.getGameStatus());
  }

  /*
  @Test
  void getGameStateUnderCheck() {
    GameRule checkRule =
        new GameRule() {
          @Override
          public int getBoardWeight() {
            return 2;
          }

          @Override
          public int getBoardHeight() {
            return 2;
          }

           *    A B
           *  2 ♔ □
           *  1 □ ♚

          @Override
          public Map<String, Cell> generateBoardCells() {
            Player whitePlayer = new Player("white");
            Player blackPlayer = new Player("black");
            Map<String, Cell> map = new HashMap<>();
            Cell emptyCell = new Cell('A', 1);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);
            emptyCell = new Cell('B', 2);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);
            Cell kingCell = new Cell('A', 2);
            kingCell.setFigure(new King(whitePlayer, "\u2654"));
            map.put(kingCell.getStringKey(), kingCell);
            kingCell = new Cell('B', 1);
            kingCell.setFigure(new King(blackPlayer, "\u265A"));
            map.put(kingCell.getStringKey(), kingCell);
            return map;
          }
        };
    gameState = new ChessGameState(new Board(checkRule));
    String expected =
        "It's "
            + gameState.getCurrentTurnPlayer()
            + " turn."
            + gameState.getCurrentTurnPlayer()
            + " is under check";
    assertEquals(expected, gameState.getGameStatus());
  }


  @Test
  void getGameStateUnderCheckMate() {
    GameRule checkMateRule =
        new GameRule() {
          @Override
          public int getBoardWeight() {
            return 3;
          }

          @Override
          public int getBoardHeight() {
            return 2;
          }


            A B C
            2 ♔ □ ♛
            1 □ □ ♛

          @Override
          public Map<String, Cell> generateBoardCells() {
            Player whitePlayer = new Player("white");
            Player blackPlayer = new Player("black");
            Map<String, Cell> map = new HashMap<>();

            Cell emptyCell = new Cell('A', 1);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            emptyCell = new Cell('B', 2);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            emptyCell = new Cell('B', 1);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            Cell kingCell = new Cell('A', 2);
            kingCell.setFigure(new King(whitePlayer, "\u2654"));
            map.put(kingCell.getStringKey(), kingCell);

            kingCell = new Cell('C', 1);
            kingCell.setFigure(new Queen(blackPlayer, "\u265B"));
            map.put(kingCell.getStringKey(), kingCell);

            kingCell = new Cell('C', 2);
            kingCell.setFigure(new Queen(blackPlayer, "\u265B"));
            map.put(kingCell.getStringKey(), kingCell);

            return map;
          }
        };
    gameState = new ChessGameState(new Board(checkMateRule));
    assertThrows(GameOverException.class, () -> gameState.getGameStatus());
  }

  @Test
  void getGameStateDraw() {
    GameRule drawRule =
        new GameRule() {
          @Override
          public int getBoardWeight() {
            return 3;
          }

          @Override
          public int getBoardHeight() {
            return 2;
          }


           *    A B C
           *  2 ♔ □ □
           *  1 □ □ ♛

          @Override
          public Map<String, Cell> generateBoardCells() {
            Player whitePlayer = new Player("white");
            Player blackPlayer = new Player("black");
            Map<String, Cell> map = new HashMap<>();

            Cell emptyCell = new Cell('A', 1);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            emptyCell = new Cell('B', 2);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            emptyCell = new Cell('B', 1);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            Cell kingCell = new Cell('A', 2);
            kingCell.setFigure(new King(whitePlayer, "\u2654"));
            map.put(kingCell.getStringKey(), kingCell);

            kingCell = new Cell('C', 1);
            kingCell.setFigure(new Queen(blackPlayer, "\u265B"));
            map.put(kingCell.getStringKey(), kingCell);

            emptyCell = new Cell('C', 2);
            emptyCell.setEmpty(true);
            map.put(emptyCell.getStringKey(), emptyCell);

            return map;
          }
        };
    gameState = new ChessGameState(new Board(drawRule));
    assertThrows(GameOverException.class, () -> gameState.getGameStatus());
  }
*/
  @Test
  void executeCommandNull() {
    assertThrows(NullPointerException.class, () -> gameState.executeCommand(null));
  }

  @Test
  void executeCommandNoCellsOnBoard() {
    String[] noFirstCoordinate = new String[] {"A9", "B2"};
    String[] noSecondCoordinate = new String[] {"A2", "I2"};

    assertThrows(NullPointerException.class, () -> gameState.executeCommand(noFirstCoordinate));
    assertThrows(NullPointerException.class, () -> gameState.executeCommand(noSecondCoordinate));
  }

  @Test
  void executeCommandNoFigureOnFirstCell() {
    String[] noFigureOnFirstCell = new String[] {"A3", "B2"};
    assertThrows(
        IllegalArgumentException.class, () -> gameState.executeCommand(noFigureOnFirstCell));
  }

  @Test
  void executeCommandFigureOnTheFirstCellBelongsToAnotherPlayer() {
    String[] coordinates = new String[] {"A8", "B2"};
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandMove() {
    String[] coordinates = new String[] {"E2", "E4"};
    gameState.executeCommand(coordinates);
    assertFalse(gameState.getGameBoard().getBoardCells().get("E4").isEmpty());
  }

  @Test
  void executeCommandBeat() {
    String[] firsMoveCoordinate = new String[] {"E2", "E4"};
    String[] secondMoveCoordinate = new String[] {"D7", "D5"};
    String[] beatCoordinate = new String[] {"E4", "D5"};

    gameState.executeCommand(firsMoveCoordinate);
    gameState.switchPlayer();
    gameState.executeCommand(secondMoveCoordinate);
    gameState.switchPlayer();
    gameState.executeCommand(beatCoordinate);

    assertNotNull(gameState.getGameBoard().getBoardCells().get("D5").getFigure());
  }

  @Test
  void executeCommandCastle() {
    String[] castleCoordinates = new String[] {"A1", "D1"};
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand(castleCoordinates));
  }
}
