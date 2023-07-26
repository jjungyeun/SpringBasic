package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repo = new MemberRepositoryV0();

    @Test
    public void crud() throws SQLException {
        // save
        Member member = new Member("memberV0", 10000);
        repo.save(member);

        // select
        Member foundMember = repo.findById(member.getMemberId());
        assertEquals(member, foundMember); // Member에 @Data 붙어있어서 값비교 가능
    }

}