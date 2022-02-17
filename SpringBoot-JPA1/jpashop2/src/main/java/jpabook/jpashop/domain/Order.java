package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    //==연관관계 메서드==//
    private void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    private void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setDelivery(Address address){
        this.delivery = Delivery.createDelivery(address, this);
    }

    private void setOrderDateTime(LocalDateTime dateTime){
        this.orderDateTime = dateTime;
    }

    private void setOrderStatus(OrderStatus status){
        this.orderStatus = status;
    }


    //==생성 메서드==//
    public static Order createOrder(Member member, Address deliveryAddress, OrderItem... orderItem){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(deliveryAddress);
        for (OrderItem item : orderItem) {
            order.addOrderItem(item);
        }
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDER);
        return order;
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancelOrder(){
        if (delivery.getDeliveryStatus() == DeliveryStatus.COMP)
            throw new IllegalStateException("이미 배송 완료된 상품입니다.");
        this.setOrderStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     *  전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
