package org.buzevych.boardgame.gamesaver;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JDBCDataSourceTest {

  @Disabled
  @Test
  void getMySQLDataSource() throws SQLException {
    DataSource dataSource = JDBCDataSource.getMySQLDataSource();
    Connection connection = dataSource.getConnection();
    assertNotNull(connection);
    assertEquals("MySQL", connection.getMetaData().getDatabaseProductName());
  }
}
