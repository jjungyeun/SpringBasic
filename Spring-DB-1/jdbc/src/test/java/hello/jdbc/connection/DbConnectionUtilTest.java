package hello.jdbc.connection;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DbConnectionUtilTest {

    @Test
    public void connection() {
        // when
        Connection connection = DbConnectionUtil.getConnection();

        // then
        assertNotNull(connection);

    }

}