package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }


    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInit1(){
            Member member = Member.createMember("userA", new Address("서울", "1", "1111"));
            em.persist(member);

            Book book1 = Book.createBook("JPA1 Book", 10000, 100, "김영한", "1111");
            em.persist(book1);
            Book book2 = Book.createBook("JPA2 Book", 20000, 100, "김영한", "1112");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);
            Order order = Order.createOrder(member, member.getAddress(), orderItem1, orderItem2);
            em.persist(order);
        }

        public void dbInit2(){
            Member member = Member.createMember("userB", new Address("경기", "2", "2222"));
            em.persist(member);

            Book book1 = Book.createBook("Spring1 Book", 20000, 200, "이일민", "2111");
            em.persist(book1);
            Book book2 = Book.createBook("Spring2 Book", 40000, 300, "이일민", "2112");
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            Order order = Order.createOrder(member, member.getAddress(), orderItem1, orderItem2);
            em.persist(order);
        }
    }
}
