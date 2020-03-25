package org.buzevych.core.boardgame.gamesaver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class JDBCGameStateSaverTest {

  @Mock private DataSource dataSource;
  @Mock private Connection connection;
  @Mock private PreparedStatement preparedStatement;
  @Mock private ResultSet resultSet;

  @BeforeEach
  void init() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
  }

  @Test
  void testCreateSave() {
    String date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).toString();
    JDBCGameStateSaver saver = new JDBCGameStateSaver("chess", dataSource);
    saver.initialize();

    assertTrue(saver.getGameID().startsWith(date));
  }

  @Test
  void testGetSave() {
    JDBCGameStateSaver saver = new JDBCGameStateSaver("chess", dataSource);
    saver.initialize();
    GameSave save = saver.load();

    assertEquals("chess", save.getGameName());
    assertEquals(saver.getGameID(), save.getUniqueSaveID());
  }

  @Test
  void testSave() throws SQLException {
    JDBCGameStateSaver saver = new JDBCGameStateSaver("chess", dataSource);
    saver.save("command");

    verify(preparedStatement).setString(1, saver.getGameID());
    verify(preparedStatement).setString(3, "command");
    verify(preparedStatement).executeUpdate();
    verify(connection).close();
  }
  @Test
  void testGetLatestSaveLoad() throws SQLException {
    String gameName = UUID.randomUUID().toString();
    when(dataSource.getConnection()).thenReturn(connection);
    JDBCGameStateSaver saver = new JDBCGameStateSaver(gameName, dataSource);
    assertFalse(saver.latestSave());
  }
}
