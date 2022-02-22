package jpabook.jpashop.repository.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    /**
     * Repository에 API 스펙이 들어감
     * -> 특정 API를 위해 DTO에 맞는 데이터를 조회한 것
     */
    public List<OrderSimpleQueryDto> findOrderDto() {
        return em.createQuery(
                        "select new jpabook.jpashop.repository.order.OrderSimpleQueryDto(o.id, m.name, o.orderDateTime, o.orderStatus, d.address) " +
                                "from Order o " +
                                "join o.member m " +
                                "join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
