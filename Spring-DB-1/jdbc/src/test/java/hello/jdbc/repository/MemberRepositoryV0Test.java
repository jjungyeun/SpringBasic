package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryV0Test {

    MemberRepositoryV0 repo = new MemberRepositoryV0();

    @Test
    public void save() throws SQLException {
        // given
        Member member = new Member("memberV0", 10000);

        // when
        repo.save(member);

        // then


    }

}