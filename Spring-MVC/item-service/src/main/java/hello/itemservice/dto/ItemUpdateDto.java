package hello.itemservice.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemUpdateDto {
    private String name;
    private Integer price;
    private Integer quantity;
}
