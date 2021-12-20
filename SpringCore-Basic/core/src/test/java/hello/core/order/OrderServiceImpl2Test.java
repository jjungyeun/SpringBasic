package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImpl2Test {

//    @Test
//    void createOrder1(){
//        // 수정자 주입을 사용하는 클래스.
//        // 순수 자바 코드를 통한 테스트이기 때문에 의존관계 주입을 하지 않아도 컴파일 오류 안남.(돌려보기 전까지 모름)
//        // 컴파일 오류는 나지않지만 NPE가 발생한다.
//        OrderServiceImpl2 orderService = new OrderServiceImpl2();
//        orderService.createOrder(1L, "memberA", 1000);
//    }


    @Test
    void createOrder2(){
        // 생성자 주입을 이용하면 파라미터를 넣지 않았을 때 컴파일 오류가 나기 때문에 실수를 줄일 수 있다.
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "memberA", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "memberA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}