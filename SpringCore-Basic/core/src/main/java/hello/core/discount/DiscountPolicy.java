package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    // JavaDoc 생성 단축키: Alt + J
    /**
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
