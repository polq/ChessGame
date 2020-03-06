package org.buzevych.boardgame.gamesaver;

import org.buzevych.boardgame.gamesaver.FileGameStateSaver;
import org.buzevych.boardgame.gamesaver.GameSave;
import org.buzevych.boardgame.gamesaver.GameStateSaver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    gameSaver.initialize();
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
    gameSaver.initialize();
    gameSaver.save("A1 A2");
    GameSave gameSave = gameSaver.load();

    assertEquals("chess", gameSave.getGameName());
    assertEquals(1, gameSave.getCommandsLog().size());
  }

  @Test
  void testGetSaveNoFile() {
    assertThrows(IllegalArgumentException.class, () -> gameSaver.load());
  }

  @Test
  void testGetSaveDamageFile() throws IOException {
    File temp = new File("game_1");
    assertTrue(temp.createNewFile());
    GameStateSaver saver = new FileGameStateSaver(temp.toPath(), "game");
    assertThrows(IllegalArgumentException.class, saver::load);
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
    gameSaver.initialize();
    GameStateSaver saver =
        new FileGameStateSaver(Path.of(tempFile.getAbsolutePath()), "chess");
    assertNotNull(saver);
  }

  @Test
  void findMostRecentSave() throws IOException {
    File tempFile = new File("game_1");
    assertTrue(tempFile.createNewFile());
    GameStateSaver saver = new FileGameStateSaver(tempFile.toPath(), "game");
    saver.initialize();

    saver = saver.latestSave();

    assertNotNull(saver);
    assertEquals("game", saver.load().getGameName());

    tempFile.deleteOnExit();
  }

  @Test
  void findMostRecentSaveNew() {
    GameStateSaver saver = new FileGameStateSaver("game").latestSave();
    assertNotNull(saver);
  }
}
