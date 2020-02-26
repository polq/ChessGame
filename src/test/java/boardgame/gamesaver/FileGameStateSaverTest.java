package boardgame.gamesaver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileGameStateSaverTest {

  public static final String STANDARD_FILE_SAVE_PATH = "game.txt";
  GameStateSaver gameSaver;

  @BeforeEach
  void init() {
    gameSaver = new FileGameStateSaver(Path.of(STANDARD_FILE_SAVE_PATH), "chess");
  }

  @Test
  void testCreateNewSave() throws IOException {
    gameSaver.createSave();

    assertEquals(2, Files.lines(Path.of(STANDARD_FILE_SAVE_PATH)).count());
  }

  @Test
  void testSaveCommand() throws IOException {
    boolean isPath = Path.of(STANDARD_FILE_SAVE_PATH).toFile().delete();
    gameSaver.save("A2 A3");
    long linesCount = Files.lines(Path.of(STANDARD_FILE_SAVE_PATH)).count();
    assertEquals(1, linesCount);
  }

  @Test
  void testGetSave() {
    gameSaver.createSave();
    gameSaver.save("A1 A2");
    GameSave gameSave = gameSaver.getSave();

    assertEquals("chess", gameSave.getGameName());
    assertEquals(1, gameSave.getCommandsLog().size());
  }

  @Test
  void testHasSave() {
    assertTrue(gameSaver.hasSave());
  }

  @Test
  void testHastSaveNoSave() {
    gameSaver = new FileGameStateSaver(Path.of("randomfile.extension"), "chess");
    assertFalse(gameSaver.hasSave());
  }
}
