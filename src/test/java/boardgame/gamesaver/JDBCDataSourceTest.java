package boardgame.gamesaver;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCDataSourceTest {

  @Test
  void getMySQLDataSource() throws SQLException {
    DataSource dataSource = JDBCDataSource.getMySQLDataSource();
    Connection connection = dataSource.getConnection();
    assertNotNull(connection);
    assertEquals("MySQL", connection.getMetaData().getDatabaseProductName());
  }
}
