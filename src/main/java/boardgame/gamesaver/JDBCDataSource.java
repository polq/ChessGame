package boardgame.gamesaver;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/** Class that is used to get Data Source from a property file for the specific DBMS. */
public class JDBCDataSource {

  /**
   * It returns DataSource for MySQL database, with the properties such as url, username and
   * password specified in a property file.
   *
   * @return {@link DataSource} of the of the MySQL database
   */
  public static DataSource getMySQLDataSource() {
    Properties props = new Properties();
    try (FileReader reader = new FileReader("db.properties")) {
      props.load(reader);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    MysqlDataSource mysqlDS;
    mysqlDS = new MysqlDataSource();
    mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
    mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
    mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
    return mysqlDS;
  }
}
