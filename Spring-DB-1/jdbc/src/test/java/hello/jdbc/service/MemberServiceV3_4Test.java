package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 트랜잭션 - DataSource, TransactionManager 자동 등록
 */
@Slf4j
@SpringBootTest
class MemberServiceV3_4Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_ERR = "err";

    @Autowired
    private MemberRepositoryV3 memberRepository;
    @Autowired
    private MemberServiceV3_3 memberService;

    @TestConfiguration // @SpringBootTest를 위한 configuration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepositoryV3 memberRepositoryV3() {
            return new MemberRepositoryV3(dataSource);
        }

        @Bean
        MemberServiceV3_3 memberServiceV3() {
            return new MemberServiceV3_3(memberRepositoryV3());
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_ERR);
    }

    @Test
    public void ApoCheck() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberRepository class={}", memberRepository.getClass());
        assertTrue(AopUtils.isAopProxy(memberService));
        assertFalse(AopUtils.isAopProxy(memberRepository));
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