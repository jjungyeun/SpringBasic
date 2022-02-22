package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.order.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * [xToOne 관계 성능 최적화]
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@Controller
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping("/api/v1/simple-orders")
    @ResponseBody
    public List<Order> ordersV1(){
        // Jackson 라이브러리가 프록시 객체를 json으로 생성하는 방법을 모름 -> 예외 발생
        // Hibernate5 모듈을 등록하면 어느정도 해결은 가능, but Lazy loading 문제 때문에 사용하기엔 오바임
        // 그냥 API에 Entity 넘기지 말기~~
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        for (Order order : orders) {
            // LAZY 강제 초기화
            order.getMember().getName();
            order.getDelivery().getDeliveryStatus();
        }
        return orders;
    }

    @GetMapping("/api/v2/simple-orders")
    @ResponseBody
    public List<SimpleOrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        // Lazy Loading 때문에 order마다 member, delivery를 조회
        // -> order 1개당 2번의 select 쿼리가 추가적으로 실행됨 (N+1 Problem)
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v3/simple-orders")
    @ResponseBody
    public List<SimpleOrderDto> ordersV3(){
        // fetch join으로 order를 조회할 때 member, delivery도 함께 select
        // V2랑은 다르게 join이 포함된 하나의 쿼리만 실행됨
        // 순수한 엔티티를 조회하는데, 성능 최적화를 위해 코드 상에서 튜닝을 한 것 (재사용성 V4보다 높음)
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    @ResponseBody
    public List<OrderSimpleQueryDto> ordersV4(){
        // V3보다 성능 최적화가 됨 (대부분은 성능 차이 미비)
        // SQL을 짜듯이 원하는 컬럼만 조회한 것
        // API 스펙에 의존하게 되어 재사용성이 매우 낮음 -> 이런 case 전용 repository를 따로 분리하는 것이 좋음
        return orderSimpleQueryRepository.findOrderDto();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();    // Lazy 초기화
            this.orderDate = order.getOrderDateTime();
            this.orderStatus = order.getOrderStatus();
            this.address = order.getDelivery().getAddress();    // Lazy 초기화
        }
    }
}
