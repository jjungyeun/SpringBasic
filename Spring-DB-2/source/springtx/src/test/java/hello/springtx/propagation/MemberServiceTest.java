package hello.springtx.propagation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired LogRepository logRepository;

    /**
     * memberService    @Transactional: OFF
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON
     */
    @Test
    public void outerTxOff_success() throws Exception {
        // given
        String username ="outerTxOff_success";

        // when
        memberService.joinV1(username);

        // then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional: OFF
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON - RuntimeException
     */
    @Test
    public void outerTxOff_fail() throws Exception {
        // given
        String username ="로그예외_outerTxOff_fail";

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> memberService.joinV1(username));
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional: ON
     * memberRepository @Transactional: OFF
     * logRepository    @Transactional: OFF
     */
    @Test
    public void singleTx() throws Exception {
        // given
        String username ="singleTx";

        // when
        memberService.joinV1(username);

        // then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional: ON
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON
     */
    @Test
    public void outerTxOn_success() throws Exception {
        // given
        String username ="outerTxOn_success";

        // when
        memberService.joinV1(username);

        // then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isPresent());
    }

    /**
     * memberService    @Transactional: ON
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON - RuntimeException
     */
    @Test
    public void outerTxOn_fail() throws Exception {
        // given
        String username ="로그예외_outerTxOn_fail";

        // when & then
        Assertions.assertThrows(RuntimeException.class, () -> memberService.joinV1(username));
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional: ON
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON - RuntimeException
     */
    @Test
    public void recoverException_fail() throws Exception {
        // given
        String username ="로그예외_recoverException_fail";

        // when & then
        Assertions.assertThrows(UnexpectedRollbackException.class, () -> memberService.joinV2(username));
        Assertions.assertTrue(memberRepository.find(username).isEmpty());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }

    /**
     * memberService    @Transactional: ON
     * memberRepository @Transactional: ON
     * logRepository    @Transactional: ON (REQUIRES_NEW) - RuntimeException
     */
    @Test
    public void recoverException_success() throws Exception {
        // given
        String username ="로그예외_recoverException_success";

        // when
        memberService.joinV2(username);

        // then
        Assertions.assertTrue(memberRepository.find(username).isPresent());
        Assertions.assertTrue(logRepository.find(username).isEmpty());
    }
}