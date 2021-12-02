package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 역할에 대한 배역을 지정하는 관리자 파일
public class AppConfig {

    public MemberService memberService(){
        // * 생성자 주입
        // MemberServiceImpl에서 MemberRepository 배역에 대한 배우는 MemoryMemberRepository로 결정
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService(){
        // * 생성자 주입
        // OrderServiceImpl에서 MemberRepository 배역에 대한 배우는 MemoryMemberRepository로,
        // DiscountPolicy 배역에 대한 배우는 FixDiscountPolicy로 결정
        return new OrderServiceImpl(
                new MemoryMemberRepository(),
                new FixDiscountPolicy());
    }
}
