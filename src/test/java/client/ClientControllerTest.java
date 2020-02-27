package client;

import boardgame.game.GameAI;
import boardgame.game.GameStarter;
import boardgame.gamesaver.FileGameStateSaver;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClientControllerTest {

  @TempDir File tempDir;

  @Test
  void testGetGameAIChess() {
    GameAI gameAI = ClientController.getGameAI("chess", null);
    assertEquals("chess", gameAI.getGameName());
  }

  @Test
  void testGetGameAICheckers() {
    GameAI gameAI = ClientController.getGameAI("checkers", null);
    assertEquals("checkers", gameAI.getGameName());
  }

  @Test
  void testGetGameAIFailGame() {
    assertThrows(IllegalArgumentException.class, () -> ClientController.getGameAI("pai sho", null));
  }

  @Test
  void testGetGameAICustomPlayers() {
    List<String> playerList = Arrays.asList("1", "2");
    GameAI gameAI = ClientController.getGameAI("chess", playerList);
    assertEquals("1", Objects.requireNonNull(gameAI.getPlayerQueue().peek()).toString());
  }

  @Test
  void getGameStarterForFileJustGame() {}

  @Test
  void testParseInputLineJustGame() {
    ClientController.Args args = ClientController.parseInputLine("chess");
    assertEquals("chess", args.gameName);
  }

  @Test
  void testParseInputLineNewGame() {
    ClientController.Args args = ClientController.parseInputLine("chess -new");
    assertTrue(args.newGame);
  }

  @Test
  void testParseInputLineLoad() {
    ClientController.Args args = ClientController.parseInputLine("chess -load file.txt");
    assertEquals("file.txt", args.gameFile);
  }

  @Test
  void testParseInputError() {
    assertThrows(
        ParameterException.class, () -> ClientController.parseInputLine("chess load file.txt"));
  }

  @Test
  void testGetGameStarterFromFile() {
    File tempFile = new File(tempDir, "file.txt");
    FileGameStateSaver stateSaver = new FileGameStateSaver(tempFile.toPath(), "chess");
    stateSaver.createSave();

    GameStarter starter =
        ClientController.getGameStarterForFile(
            ClientController.parseInputLine("chess -load " + tempFile.getAbsolutePath()));
    assertNotNull(starter);
  }

  @Test
  void testGetGameStarterFromFileNew() {
    GameStarter starter =
        ClientController.getGameStarterForFile(ClientController.parseInputLine("chess -new"));
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
    GameStarter starter =
        ClientController.getGameStarterForFile(ClientController.parseInputLine("chess"));
    assertNotNull(starter);
    tempFile.deleteOnExit();
  }
}
