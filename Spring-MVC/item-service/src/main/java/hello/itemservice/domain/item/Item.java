package hello.itemservice.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Item {
    private Long id;
    private String itemName;

    // nullable하기 위해 int 대신 Integer 사용
    private Integer price;
    private Integer quantity;

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    protected void setId(Long id) {
        this.id = id;
    }
}
