package boardgame.gamesaver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
  void testGetSaveNoFile() {
    assertThrows(IllegalArgumentException.class, () -> gameSaver.getSave());
  }

  @Test
  void testGetSaveDamageFile() throws IOException {
    File temp = new File("game_1");
    assertTrue(temp.createNewFile());
    GameStateSaver saver = new FileGameStateSaver(temp.toPath(), "game");
    assertThrows(IllegalArgumentException.class, saver::getSave);
    assertTrue(temp.delete());
  }

  @Test
  void testHastSaveNoSave() {
    File anotherTempFile = new File(tempDir, "anotherFile");
    gameSaver = new FileGameStateSaver(anotherTempFile.toPath(), "chess");
    assertFalse(anotherTempFile.exists());
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
  void findMostRecentSave() throws IOException {
    File tempFile = new File("game_1");
    assertTrue(tempFile.createNewFile());
    GameStateSaver saver = new FileGameStateSaver(tempFile.toPath(), "game");
    saver.createSave();

    saver = FileGameStateSaver.findMostRecentFileSave("game");

    assertNotNull(saver);
    assertEquals("game", saver.getSave().getGameName());

    tempFile.deleteOnExit();
  }

  @Test
  void findMostRecentSaveNew() {
    GameStateSaver saver = FileGameStateSaver.findMostRecentFileSave("game");
    assertNotNull(saver);
  }
}
