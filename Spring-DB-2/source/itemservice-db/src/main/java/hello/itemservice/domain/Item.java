package hello.itemservice.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)    // length값은 DDL용
    private String itemName;
    private Integer price;
    private Integer quantity;

    // JPA 기본 스펙 - 엔티티는 public 또는 protected 기본 생성자 필요
    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
