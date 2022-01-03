package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity){
        // 파라미터로 입력받은 준영속 엔티티와 같은 엔티티를 영속성 컨텍스트에서 조회한다.
        Book book = (Book) itemRepository.findOne(itemId);
        // 데이터를 수정한다. -> 트랜잭션 커밋 시점에 변경 감지가 일어난다.
        book.changeItem(name, price, stockQuantity);
        return book;
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
