package jpabook.jpashop.main;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {
    public static void main(String[] args) {
        // EMF는 애플리케이션 실행 단위마다 하나만 생성해야 함
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 트랜잭션 단위로 뭔가를 수행할 때는 EM을 생성하여 사용
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Member member = new Member();
            member.setName("WJY");
            member.setAddress(new Address("용인시", "마북로 210", "16911"));
            em.persist(member);

            transaction.commit();
        }
        catch (Exception e){
            transaction.rollback();
        }
        finally {
            em.close();
        }

        emf.close();
    }
}
