package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 역할에 대한 배역을 지정하는 관리자 파일
// 객체를 생성하고 관리하면서 의존관계를 연결해주는 DI 컨테이너
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        // * 생성자 주입
        // MemberServiceImpl에서 MemberRepository 배역에 대한 배우는 MemoryMemberRepository로 결정
//        return new MemberServiceImpl(new MemoryMemberRepository());
        // 역할과 구현이 잘 드러나도록 표현
        return new MemberServiceImpl(memberRepository());
    }

    // 메소드 명에 역할이 드러남
    // 반환 객체에서는 구현체가 보임
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        // * 생성자 주입
        // OrderServiceImpl에서 MemberRepository 배역에 대한 배우는 MemoryMemberRepository로,
        // DiscountPolicy 배역에 대한 배우는 FixDiscountPolicy로 결정
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
