package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repo;

    @BeforeEach
    void setUp() {
//        // 기본 - DriverManager - 항상 새로운 커넥션 획득
//        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");

        // DataSource 주입
        repo = new MemberRepositoryV1(dataSource);
    }

    @Test
    public void crud() throws SQLException {
        // save
        Member member = new Member("memberV0", 10000);
        repo.save(member);

        // select
        Member foundMember = repo.findById(member.getMemberId());
        assertEquals(member, foundMember); // Member에 @Data 붙어있어서 값비교 가능

        // update: money 10000 -> 20000
        repo.update(member.getMemberId(), 20000);
        Member updatedMember = repo.findById(member.getMemberId());
        assertEquals(20000, updatedMember.getMoney());

        // delete
        repo.delete(member.getMemberId());
        assertThrows(NoSuchElementException.class, () -> repo.findById(member.getMemberId()));
    }

}