package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

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

        // update: money 10000 -> 20000
        repo.update(member.getMemberId(), 20000);
        Member updatedMember = repo.findById(member.getMemberId());
        assertEquals(20000, updatedMember.getMoney());

        // delete
        repo.delete(member.getMemberId());
        assertThrows(NoSuchElementException.class, () -> repo.findById(member.getMemberId()));
    }

}