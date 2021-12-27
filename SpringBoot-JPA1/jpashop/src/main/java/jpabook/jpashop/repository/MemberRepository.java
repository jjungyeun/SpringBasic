package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // 스프링 빈 주입을 위한 애노테이션
//    @PersistenceContext
//    private EntityManager em;

    // 스프링 데이터 JPA에서는 @PersistenceContext를 @Autowired로 대체 가능
    // 생성자 주입 사용 가능 (@RequiredArgsConstructor)
    private final EntityManager em;

    public void save(Member member){
        // insert
        em.persist(member);
    }

    public Member findOne(Long id){
        // 단건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        // JPQL 사용. 엔티티로 조회
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
