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
            // ID가 있다는 것은 이미 DB에 들어있는걸 사용하려고 가져온 것(준영속 엔티티)
            // merge를 사용하면 item과 동일한 식별자를 갖는 엔티티를 DB에서 찾아온 뒤(영속 엔티티)
            // 불러온 영속 엔티티를 item으로 바꿔치기한다. (모든 값 변경)
            // 그러면 영속 엔티티가 변경되었기 때문에 트랜잭션이 커밋될 때 업데이트가 일어난다.
            em.merge(item);
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
