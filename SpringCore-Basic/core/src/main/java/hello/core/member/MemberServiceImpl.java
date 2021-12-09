package hello.core.member;

public class MemberServiceImpl implements MemberService {

//    // MemberRepository에 대한 구현 객체 선택
//    // 추상화에도, 구체화에도 의존 -> DIP 위반
//    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // 생성자를 통해서 memberRepository에 뭐가 들어갈지 선택하도록 만듦 (구현체에 대한 내용이 없어짐) -> DIP 지킴
    private final MemberRepository memberRepository;
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        // MemoryMemberRepository가 Override한 save() 메소드가 실행 됨
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Test 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
