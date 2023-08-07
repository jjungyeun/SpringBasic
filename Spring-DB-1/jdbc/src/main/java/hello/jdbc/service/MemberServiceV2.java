package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection conn = dataSource.getConnection();
        try {
            conn.setAutoCommit(false); // 트랜잭션 시작
            bizLogic(conn, fromId, toId, money); // 비즈니스 로직
            conn.commit(); // 트랜잭션 커밋

        } catch (Exception e) {
            conn.rollback(); // 실패 시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(conn);
        }

    }

    private void bizLogic(Connection conn, String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(conn, fromId);
        Member toMember = memberRepository.findById(conn, toId);

        memberRepository.update(conn, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(conn, toId, toMember.getMoney() + money);
    }

    private static void release(Connection conn) {
        if (conn != null) {
            try {
                conn.setAutoCommit(true); // 커넥션풀로 돌아가기 전에 자동커밋 설정을 되돌린다
                conn.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    private static void validation(Member toMember) {
        if (toMember.getMemberId().equals("err")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
