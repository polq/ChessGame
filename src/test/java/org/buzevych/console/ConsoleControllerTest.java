package org.buzevych.console;

import org.buzevych.console.ConsoleController;
import org.buzevych.core.boardgame.game.GameAI;
import org.buzevych.core.boardgame.game.GameStarter;
import org.buzevych.core.boardgame.gamesaver.FileGameStateSaver;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsoleControllerTest {

  @TempDir File tempDir;

  @Test
  void testGetGameAIChess() {
    GameAI gameAI = ConsoleController.nameGameAI("chess", null);
    assertEquals("chess", gameAI.getGameName());
  }

  @Test
  void testGetGameAICheckers() {
    GameAI gameAI = ConsoleController.nameGameAI("checkers", null);
    assertEquals("checkers", gameAI.getGameName());
  }

  @Test
  void testGetGameAIFailGame() {
    assertThrows(
        IllegalArgumentException.class, () -> ConsoleController.nameGameAI("pai sho", null));
  }

  @Test
  void testGetGameAICustomPlayers() {
    List<String> playerList = Arrays.asList("1", "2");
    GameAI gameAI = ConsoleController.nameGameAI("chess", playerList);
    assertEquals("1", Objects.requireNonNull(gameAI.getPlayerQueue().peek()).toString());
  }

  @Test
  void getGameStarterForFileJustGame() {}

  @Test
  void testParseInputLineJustGame() {
    ConsoleController.Args args = ConsoleController.parseInputLine("chess");
    assertEquals("chess", args.gameName);
  }

  @Test
  void testParseInputLineNewGame() {
    ConsoleController.Args args = ConsoleController.parseInputLine("chess -new");
    assertTrue(args.newGame);
  }

  @Test
  void testParseInputLineLoad() {
    ConsoleController.Args args = ConsoleController.parseInputLine("chess -load file.txt");
    assertEquals("file.txt", args.gameFile);
  }

  @Test
  void testParseInputError() {
    assertThrows(
        ParameterException.class, () -> ConsoleController.parseInputLine("chess load file.txt"));
  }

  @Test
  void testGetGameStarterFromFile() {
    File tempFile = new File(tempDir, "file.txt");
    FileGameStateSaver stateSaver = new FileGameStateSaver(tempFile.toPath(), "chess");
    stateSaver.initialize();

    GameStarter starter =
        ConsoleController.fileStarter(
            ConsoleController.parseInputLine("chess -load " + tempFile.getAbsolutePath()));
    assertNotNull(starter);
  }

  @Test
  void testGetGameStarterFromFileNew() {
    GameStarter starter =
        ConsoleController.fileStarter(ConsoleController.parseInputLine("chess -new"));
    starter.getStartedGameSnap();
    Optional<File> files =
        Arrays.stream(Objects.requireNonNull(Path.of(".").toFile().listFiles()))
            .filter(file -> file.getName().startsWith("chess_"))
            .max(Comparator.naturalOrder());

    assertTrue(files.isPresent());
    files.get().deleteOnExit();
  }

  @Test
  void testGetGameStarterFromFileLatestSave() throws IOException {
    File tempFile = new File("chess_9");
    assertTrue(tempFile.createNewFile());
    GameStarter starter = ConsoleController.fileStarter(ConsoleController.parseInputLine("chess"));
    assertNotNull(starter);
    tempFile.deleteOnExit();
  }
}
