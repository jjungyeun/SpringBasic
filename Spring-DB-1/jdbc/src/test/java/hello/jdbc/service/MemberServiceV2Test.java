package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 기본 동작, 트랜잭션이 없어서 문제 발생
 */
@Slf4j
class MemberServiceV2Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_ERR = "err";

    private MemberRepositoryV2 memberRepository;
    private MemberServiceV2 memberService;

    @BeforeEach
    void setUp(){
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepository = new MemberRepositoryV2(dataSource);
        memberService = new MemberServiceV2(dataSource, memberRepository);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_ERR);
    }

    @Test
    @DisplayName("정상 이체")
    public void accountTransfer() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberB = new Member(MEMBER_B, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        // when
        log.info("START Transaction");
        memberService.accountTransfer(MEMBER_A, MEMBER_B, 2000);
        log.info("END Transaction");

        // then
        Member findMemberA = memberRepository.findById(MEMBER_A);
        Member findMemberB = memberRepository.findById(MEMBER_B);
        assertEquals(8000, findMemberA.getMoney());
        assertEquals(12000, findMemberB.getMoney());
    }

    @Test
    @DisplayName("이체 중 오류 발생")
    public void accountTransferErr() throws SQLException {
        // given
        Member memberA = new Member(MEMBER_A, 10000);
        Member memberErr = new Member(MEMBER_ERR, 10000);
        memberRepository.save(memberA);
        memberRepository.save(memberErr);

        // when
        log.info("START Transaction");
        assertThrows(IllegalStateException.class, () -> memberService.accountTransfer(MEMBER_A, MEMBER_ERR, 2000));
        log.info("END Transaction");


        // then
        Member findMemberA = memberRepository.findById(MEMBER_A);
        Member findMemberErr = memberRepository.findById(MEMBER_ERR);
        assertEquals(10000, findMemberA.getMoney());
        assertEquals(10000, findMemberErr.getMoney());
    }

}