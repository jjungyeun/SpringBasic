package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
    public static void main(String[] args) {

        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        OrderService orderService = ac.getBean("orderService", OrderService.class);

        Long memberAId = 1L;
        Member memberA = new Member(memberAId, "memberA", Grade.VIP);
        memberService.join(memberA);

        Order orderA = orderService.createOrder(memberAId, "itemA", 20000);
        System.out.println("orderA = " + orderA);
    }
}
