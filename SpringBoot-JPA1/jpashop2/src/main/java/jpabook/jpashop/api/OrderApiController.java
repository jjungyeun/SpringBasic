package jpabook.jpashop.api;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.OrderRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            // LAZY 강제 초기화
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.forEach(orderItem -> orderItem.getItem().getName());
        }
        return orders;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        // 1+N 문제로 인해 쿼리가 매~우 많이 나가게 됨!
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        // fetch join을 사용하게되면서 쿼리가 단 1개만 나감!
        // -> 성능 많이 해결됨
        List<Order> orders = orderRepository.findAllWithItem();
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_paging(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                          @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDeliveryWithPaging(offset, limit);
        return orders.stream()
                .map(OrderDto::new)
                .collect(toList());
    }



    @Data
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;    // Value Object는 Entity가 아니므로 상관없음
        private List<OrderItemDto> orderItems;  // Dto 내에도 Entity 있으면 안됨! (Entity를 Wrapping하는 것도 안됨)

        public OrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDateTime();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto{
        private String name;
        private int count;
        private int orderPrice;
        private int totalPrice;

        public OrderItemDto(OrderItem orderItem) {
            this.name = orderItem.getItem().getName();
            this.count = orderItem.getCount();
            this.orderPrice = orderItem.getOrderPrice();
            this.totalPrice = orderItem.getTotalPrice();
        }
    }
}
