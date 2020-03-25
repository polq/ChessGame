package org.buzevych.web.mvc.service;

import org.buzevych.core.boardgame.game.GameSnapshot;
import org.buzevych.core.boardgame.game.GameStarter;
import org.buzevych.core.boardgame.items.board.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebGameServiceTest {

  WebGameService service;
  @Mock GameStarter starter;
  @Mock GameSnapshot snapshot;

  @BeforeEach
  void inti() {
    service = new WebGameService();
    service.setStarter(starter);
  }

  @Test
  void testStartGameNew() {
    GameSnapshot snapshot = service.startGame("Chess", "File", true);
    File tempFile = new File("Chess_" + LocalDateTime.now().toString().trim().split(":")[0]);
    assertTrue(tempFile.exists());
    assertNotNull(snapshot);
    assertTrue(tempFile.delete());
  }

  @Test
  void testStartGameNotNewDamaged() throws IOException {
    UUID uuid = UUID.randomUUID();
    File file = new File("Chess_" + uuid.toString());
    assertTrue(file.createNewFile());
    file.deleteOnExit();
    assertThrows(IllegalArgumentException.class, () -> service.startGame("Chess", "File", false));
    assertTrue(file.delete());
  }

  @Test
  void testStartNewGameInvalidGame() {
    assertThrows(IllegalArgumentException.class, () -> service.startGame("Paj sho", "File", true));
  }

  @Test
  void testStartNewGameInvalidSourceType() {
    assertThrows(
        IllegalArgumentException.class, () -> service.startGame("Chess", "WebSocket", true));
  }

  @Test
  void testStartGameNotNewNoSave() {
    assertThrows(IllegalArgumentException.class, () -> service.startGame("Chess", "File", false));
  }

  @Test
  void testStartGameNotNullStarter() {
    service = new WebGameService();
    GameSnapshot snapshot = service.startGame("Chess", "File", true);
    File file = new File("Chess_" + LocalDateTime.now().toString().trim().split(":")[0]);
    assertTrue(file.exists());
    assertNotNull(snapshot);
    assertTrue(file.delete());
  }

  @Test
  void testLoadGameFromFileDamaged(@Mock MultipartFile file, @Mock Board board) {
    when(file.getOriginalFilename()).thenReturn("FileName");
    assertThrows(IllegalArgumentException.class, () -> service.loadGameFromFile("Chess", file));
  }

  @Test
  void testLoadGameFromDamagedFile(@Mock MultipartFile file) {
    service = new WebGameService();
    when(file.getOriginalFilename()).thenReturn("FileName");

    assertThrows(IllegalArgumentException.class, () -> service.loadGameFromFile("Checkers", file));
  }

  @Test
  void testPlay() {
    when(starter.play(anyString())).thenReturn(snapshot);
    GameSnapshot snapshot = service.play("E2 E4");

    assertEquals(this.snapshot, snapshot);
  }

  @Test
  void testPlayWithoutStarting() {
    service = new WebGameService();
    assertThrows(NullPointerException.class, () -> service.play("E2 E4"));
  }
}
