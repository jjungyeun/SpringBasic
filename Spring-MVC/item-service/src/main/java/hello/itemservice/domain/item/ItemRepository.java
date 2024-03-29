package hello.itemservice.domain.item;

import hello.itemservice.dto.ItemDto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ItemRepository {
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 1L;

    public Item save(Item item){
        item.setId(sequence++);
        store.put(item.getId(), item);
        return item;
    }

    public Optional<Item> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
    }

    public Optional<Item> update(Long itemId, ItemDto updateDto) {
        Optional<Item> optionalItem = findById(itemId);
        if (optionalItem.isPresent()){
            Item findItem = optionalItem.get();
            if (updateDto.getName() != null)
                findItem.setItemName(updateDto.getName());
            if (updateDto.getPrice() != null)
                findItem.setPrice(updateDto.getPrice());
            if (updateDto.getQuantity() != null)
                findItem.setQuantity(updateDto.getQuantity());
        }
        return optionalItem;
    }

    public void clearStore(){
        store.clear();
    }
}
