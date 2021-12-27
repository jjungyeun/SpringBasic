package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if (item.getId() == null){  // JPA로 저장하기 전까지 ID값이 없음 (새로 생성하는 객체임)
            em.persist(item);
        } else {
            em.merge(item); // ID가 있다는 것은 이미 DB에 들어있는걸 다른 데서 가져온 것. 따라서 업데이트와 유사한 개념의 merge 수행
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
