package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepository;
import hello.jdbc.repository.MemberRepositoryV4_1;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 예외 누수 문제 해결
 * SQLException 제거
 * MemberRepository 인터페이스 의존
 */
@Slf4j
@SpringBootTest
class MemberServiceV4Test {

    private static final String MEMBER_A = "memberA";
    private static final String MEMBER_B = "memberB";
    private static final String MEMBER_ERR = "err";

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberServiceV4 memberService;

    @TestConfiguration
    static class TestConfig {

        private final DataSource dataSource;

        public TestConfig(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        MemberRepository memberRepository() {
            return new MemberRepositoryV4_1(dataSource);
        }

        @Bean
        MemberServiceV4 memberServiceV4() {
            return new MemberServiceV4(memberRepository());
        }
    }

    @AfterEach
    void tearDown() {
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
    public void accountTransfer() {
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
    public void accountTransferErr() {
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