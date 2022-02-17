package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Test
    public void 상품_주문() throws Exception {
        // given
        int bookPrice = 15000;
        int bookStockQuantity = 10;
        int orderCount = 5;

        Long memberId = createMember();
        Item book = createBook(bookPrice, bookStockQuantity);

        // when
        Long orderId = orderService.order(memberId, book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, findOrder.getOrderStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, findOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", bookPrice * orderCount, findOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.", bookStockQuantity -orderCount, book.getStockQuantity());
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품_주문_재고수량_초과() throws Exception {
        // given
        int bookPrice = 15000;
        int bookStockQuantity = 10;
        int orderCount = 15;

        Long memberId = createMember();
        Item book = createBook(bookPrice, bookStockQuantity);

        // when
        orderService.order(memberId, book.getId(), orderCount);

        //then
        fail("재고 수량보다 많이 주문할 수 없다.");

    }

    @Test
    public void 주문_취소() throws Exception {
        // given
        int bookPrice = 15000;
        int bookStockQuantity = 10;
        int orderCount = 5;

        Long memberId = createMember();
        Item book = createBook(bookPrice, bookStockQuantity);
        Long orderId = orderService.order(memberId, book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소 시 상태는 CANCEL", OrderStatus.CANCEL, findOrder.getOrderStatus());
        assertEquals("주문 수량만큼 재고가 복구되어야 한다.", bookStockQuantity, book.getStockQuantity());


    }

    private Long createMember() {
        Member member = Member.createMember("wjy", new Address("youngin", "mabukro", "16911"));
        return memberService.join(member);
    }

    private Item createBook(int bookPrice, int bookStockQuantity) {
        Item book = Book.createBook("book1", bookPrice, bookStockQuantity, "wjy", "12345");
        itemService.saveItem(book);
        return book;
    }
}