package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void tearDown(){
        memberRepository.clearStore();
    }

    @Test
    public void save() throws Exception{
        // given
        Member member = new Member("hello", 20);

        // when
        Member savedMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(savedMember.getId());
        Assertions.assertEquals(savedMember, findMember);
    }

    @Test
    public void findAll() throws Exception{
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> result = memberRepository.findAll();

        // then
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(member1));
        Assertions.assertTrue(result.contains(member2));

    }
}