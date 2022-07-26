package hello.itemservice.domain.item;

import hello.itemservice.dto.ItemUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void tearDown(){
        itemRepository.clearStore();
    }

    @Test
    public void save() throws Exception{
        // given
        Item item = new Item("itemA", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(savedItem.getId())
                .orElse(null);
        assertNotNull(findItem);
        assertEquals(item, findItem);

    }

    @Test
    public void findAll() throws Exception{
        // given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 15000, 20);
        Item savedItem1 = itemRepository.save(item1);
        Item savedItem2 = itemRepository.save(item2);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertEquals(2, items.size());
    }

    @Test
    public void updateItem() throws Exception{
        // given
        Item item = new Item("itemA", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        ItemUpdateDto updateDto = ItemUpdateDto.builder()
                .name("itemB")
                .price(2000)
                .build();
        itemRepository.update(itemId, updateDto);

        // then
        Item findItem = itemRepository.findById(itemId)
                .orElse(null);
        assertNotNull(findItem);
        assertEquals("itemB", findItem.getItemName());
        assertEquals(2000, findItem.getPrice());
        assertEquals(10, findItem.getQuantity());

    }
}