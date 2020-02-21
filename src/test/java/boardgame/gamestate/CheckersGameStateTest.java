package boardgame.gamestate;

import boardgame.items.boardcell.Cell;
import boardgame.items.boardcell.CheckersBoardFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckersGameStateTest {

  CheckersGameState gameState;

  @BeforeEach
  void init() {
    gameState = new CheckersGameState(new CheckersBoardFactory().createBoard());
  }

  @Test
  void getGameStatusActive() {
    assertEquals("It's " + gameState.getCurrentTurnPlayer() + " turn", gameState.getGameStatus());
  }
  /*
  @Test
  void getGameStatusEnded() {
    GameRule rule =
        new GameRule() {
          @Override
          public int getBoardWeight() {
            return 1;
          }

          @Override
          public int getBoardHeight() {
            return 1;
          }

          @Override
          public Map<String, Cell> generateBoardCells() {
            return Collections.emptyMap();
          }

          @Override
          public Queue<Player> generatePlayerQueue() {
            Queue<Player> que = new LinkedList<>();
            que.add(new Player("white"));
            return que;
          }
        };
    gameState = new CheckersGameState(new Board(rule));
    assertThrows(GameOverException.class, () -> gameState.getGameStatus());
  }

   */

  @Test
  void executeCommandNull() {
    assertThrows(NullPointerException.class, () -> gameState.executeCommand(null));
  }

  @Test
  void executeCommandNullFirstCell() {
    String[] coordinates = new String[] {"H9", "A2"};
    assertThrows(NullPointerException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandNullOtherCell() {
    String[] coordinates = new String[] {"B3", "B2", "I9"};
    assertThrows(NullPointerException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandNoFigureInFirstCell() {
    String[] coordinates = new String[] {"A1", "B2", "A7"};
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandFirstFigureAnotherOwner() {
    String[] coordinates = new String[] {"A8", "B2", "A7"};
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandOtherCellsAreNotEmpty() {
    String[] coordinates = new String[] {"B3", "C8", "A7"};
    assertThrows(IllegalArgumentException.class, () -> gameState.executeCommand(coordinates));
  }

  @Test
  void executeCommandMove() {
    String[] coordinates = new String[] {"B3", "A4"};
    gameState.executeCommand(coordinates);
    assertNotNull(gameState.getGameBoard().getBoardCells().get("A4").getFigure());
  }

  @Test
  void executeCommandBeat() {
    String[] coordinatesFirstMove = new String[] {"B3", "A4"};
    String[] coordinatesSecondMove = new String[] {"C6", "B5"};
    String[] coordinatesBeat = new String[] {"A4", "C6"};
    gameState.executeCommand(coordinatesFirstMove);
    gameState.switchPlayer();
    gameState.executeCommand(coordinatesSecondMove);
    gameState.switchPlayer();
    gameState.executeCommand(coordinatesBeat);

    assertNotNull(gameState.getGameBoard().getBoardCells().get("C6").getFigure());
    assertTrue(gameState.getGameBoard().getBoardCells().get("B5").isEmpty());
  }
  /*
    @Test
    void executeCommandMultipleBeat() {

      GameRule rule =
          new GameRule() {
            @Override
            public int getBoardWeight() {
              return 3;
            }

            @Override
            public int getBoardHeight() {
              return 5;
            }


             A B C
            5 □ □ □
            4 □ ⚈ □
            3 □ □ □
            2 □ ⚈ □
            1 ⚆ □ □

          @Override
          public Map<String, Cell> generateBoardCells() {
            Map<String, Cell> map = new HashMap<>();
            for (int i = 1; i <= getBoardHeight(); i++) {
              for (int j = 'A'; j < 'A' + getBoardWeight(); j++) {
                Cell newCell = new Cell((char) j, i);
                newCell.setEmpty(true);
                map.put(newCell.getStringKey(), newCell);
              }
            }
            Cell cell = map.get("A1");
            cell.figureMovedToThisCell(new Checker(new Player("white"), "\u2686"));
            cell = map.get("B2");
            cell.figureMovedToThisCell(new Checker(new Player("black"), "\u2688"));
            cell = map.get("B4");
            cell.figureMovedToThisCell(new Checker(new Player("black"), "\u2688"));
            return map;
          }
        };
    gameState = new CheckersGameState(rule.createBoard());
    String[] coordinatesBeat = new String[] {"A1", "C3", "A5"};
    gameState.executeCommand(coordinatesBeat);

    assertFalse(gameState.getGameBoard().getBoardCells().get("A5").isEmpty());
  }
  */

  @Test
  void testFindFiguresBetweenToBeat() {
    assertTrue(gameState.isOnlyOneFigureBetweenToBeat(new Cell('B', 3), new Cell('F', 7)));
  }
}
