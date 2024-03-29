package hello.itemservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDto {
    private String name;
    private Integer price;
    private Integer quantity;
}
