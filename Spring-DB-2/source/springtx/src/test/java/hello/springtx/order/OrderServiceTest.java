package hello.springtx.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired OrderService service;
    @Autowired OrderRepository repository;

    @Test
    public void complete() throws Exception {
        // given
        Order order = new Order();
        order.setUsername("정상");

        // when
        service.order(order);

        // then
        Order findOrder = repository.findById(order.getId()).get();
        Assertions.assertEquals("완료", findOrder.getPayStatus());
    }

    @Test
    public void runtimeException() throws Exception {
        // given
        Order order = new Order();
        order.setUsername("예외");

        // when
        Assertions.assertThrows(RuntimeException.class, () -> service.order(order));

        // then
        Optional<Order> orderOptional = repository.findById(order.getId());
        Assertions.assertTrue(orderOptional.isEmpty());
    }

    @Test
    public void bizException() throws Exception {
        // given
        Order order = new Order();
        order.setUsername("잔고부족");

        // when
        try{
            service.order(order);
        } catch (NotEnoughMoneyException e) {
            log.info("고객에게 잔고 부족을 알리고 별도의 계좌로 입금하도록 안내");
        }

        // then
        Order findOrder = repository.findById(order.getId()).get();
        Assertions.assertEquals("대기", findOrder.getPayStatus());
    }

}