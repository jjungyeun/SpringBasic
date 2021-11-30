package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // MemberRepository에 대한 구현 객체 선택
    // 추상화에도, 구체화에도 의존 -> DIP 위반
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        // MemoryMemberRepository가 Override한 save() 메소드가 실행 됨
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
