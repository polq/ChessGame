package boardgame.gamesaver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileGameStateSaverTest {

  @TempDir File tempDir;
  File tempFile;
  GameStateSaver gameSaver;

  @BeforeEach
  void init() {
    tempFile = new File(tempDir, "file.txt");
    gameSaver = new FileGameStateSaver(tempFile.toPath(), "chess");
  }

  @Test
  void testCreateNewSave() throws IOException {
    gameSaver.createSave();
    assertEquals(2, Files.lines(tempFile.toPath()).count());
  }

  @Test
  void testSaveCommand() throws IOException {
    gameSaver.save("A2 A3");
    long linesCount = Files.lines(tempFile.toPath()).count();
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
    assertFalse(gameSaver.hasSave());
  }

  @Test
  void testHastSaveNoSave() {
    File anotherTempFile = new File(tempDir, "anotherFile");
    gameSaver = new FileGameStateSaver(anotherTempFile.toPath(), "chess");
    assertFalse(gameSaver.hasSave());
  }

  @Test
  void testGetGameStateFromAFile() {
    gameSaver.createSave();
    GameStateSaver saver =
        FileGameStateSaver.getGameStateFromAFile(tempFile.getAbsolutePath(), "chess");
    assertNotNull(saver);
  }

  @Test
  void testGetGameStateFromAFileWrongGame() {
    gameSaver.createSave();
    assertThrows(
        IllegalArgumentException.class,
        () -> FileGameStateSaver.getGameStateFromAFile(tempFile.getAbsolutePath(), "checkers"));
  }

  @Test
  void testFindMostRecentSave() throws IOException {
    File tempFileOne = new File("game_1");
    File tempFileTwo = new File("game_2");
    assertTrue(tempFileOne.createNewFile());
    assertTrue(tempFileTwo.createNewFile());
    Optional<String> recentSave = FileGameStateSaver.findMostRecentFileSave("game");

    assertTrue(recentSave.isPresent());
    assertEquals("game_2", recentSave.get());

    tempFileOne.deleteOnExit();
    tempFileTwo.deleteOnExit();
  }

  @Test
  void testFindMostRecentNoFiles() {
    Optional<String> recentSave = FileGameStateSaver.findMostRecentFileSave("randomGame");
    assertTrue(recentSave.isEmpty());
  }
}
