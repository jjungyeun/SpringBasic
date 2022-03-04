package jpabook.jpashop.repository.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    // Repository가 Controller를 참조하지 않게하기 위해 QueryDto를 별도로 만듦
    public List<OrderQueryDto> findOrderQueryDtos() {
        // for문 내에서 쿼리를 날리기 때문에 1+N 문제 발생
        List<OrderQueryDto> result = findOrders();
        result.forEach(orderQueryDto -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(orderQueryDto.getOrderId());
            orderQueryDto.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllByDto_optimzation() {
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = getOrderIds(result);

        // 관련된 orderItem들을 전부 조회하고 메모리 상에서 for문을 돌려서 set 해주는 것이기 때문에 쿼리가 2번만 나감 (최적화 됨)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    public List<OrderFlatDto> findAllByDto_flat() {
        // 쿼리가 한번에 나감
        // order 기준 paging 불가능 (orderItem이 기준이 됨)
        return em.createQuery("select new jpabook.jpashop.repository.query.OrderFlatDto(o.id, m.name, o.orderDateTime, o.orderStatus, d.address, i.name, oi.orderPrice, oi.count) from Order o" +
                " join o.member m" +
                " join o.delivery d" +
                " join o.orderItems oi" +
                " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream().collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<Long> getOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.query.OrderItemQueryDto(i.id, i.name, oi.orderPrice, oi.count)" +
                " from OrderItem oi" +
                " join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.query.OrderQueryDto(o.id, m.name, o.orderDateTime, o.orderStatus, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
