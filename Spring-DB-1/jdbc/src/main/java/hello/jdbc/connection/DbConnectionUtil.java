package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class DbConnectionUtil {

    // Connection: JDBC 표준 인터페이스 중 하나 (java.sql.Connection)
    public static Connection getConnection() {
        try {
            // DriveManager가 알아서 DB 라이브러리로부터 Connection 구현체를 불러온다
            // ex) H2 -> org.h2.jdbc.JdbcConnection
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            log.info("get connection={}, class={}", connection, connection.getClass());
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
