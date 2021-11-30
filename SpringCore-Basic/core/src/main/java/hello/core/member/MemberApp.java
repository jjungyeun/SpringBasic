package hello.core.member;

public class MemberApp {
    // 단축기: psvm
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        // soutv하면 자동으로 변수에 대한 프린트 생성됨
        // 그냥 빈 프린트 하고 싶으면 sout만 치면 됨
        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
