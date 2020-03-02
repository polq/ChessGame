package client;

import boardgame.game.GameAI;
import boardgame.game.GameStarter;
import boardgame.gamesaver.FileGameStateSaver;
import com.beust.jcommander.ParameterException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

  @TempDir File tempDir;

  @Test
  void testGetGameAIChess() {
    GameAI gameAI = ClientController.nameGameAI("chess", null);
    assertEquals("chess", gameAI.getGameName());
  }

  @Test
  void testGetGameAICheckers() {
    GameAI gameAI = ClientController.nameGameAI("checkers", null);
    assertEquals("checkers", gameAI.getGameName());
  }

  @Test
  void testGetGameAIFailGame() {
    assertThrows(IllegalArgumentException.class, () -> ClientController.nameGameAI("pai sho", null));
  }

  @Test
  void testGetGameAICustomPlayers() {
    List<String> playerList = Arrays.asList("1", "2");
    GameAI gameAI = ClientController.nameGameAI("chess", playerList);
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
    stateSaver.initialize();

    GameStarter starter =
        ClientController.fileStarter(
            ClientController.parseInputLine("chess -load " + tempFile.getAbsolutePath()));
    assertNotNull(starter);
  }

  @Test
  void testGetGameStarterFromFileNew() {
    GameStarter starter =
        ClientController.fileStarter(ClientController.parseInputLine("chess -new"));
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
        ClientController.fileStarter(ClientController.parseInputLine("chess"));
    assertNotNull(starter);
    tempFile.deleteOnExit();
  }

  @Disabled("TODO")
  @Test
  void testGetGameStarterFromFileLoad(
      @Mock DataSource dataSource,
      @Mock Connection connection,
      @Mock PreparedStatement preparedStatement,
      @Mock ResultSet resultSet)
      throws SQLException {

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);

    GameStarter starter =
        ClientController.DBStarter(ClientController.parseInputLine("chess"));
    starter.getStartedGameSnap();
    assertNotNull(starter);
  }
}
