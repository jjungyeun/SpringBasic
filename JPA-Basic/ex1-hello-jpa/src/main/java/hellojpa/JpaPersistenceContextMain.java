package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaPersistenceContextMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {

            // 비영속 상태
            Member member = new Member();
            member.setId(2L);
            member.setName("youngjin");

            // 영속 상태 -> persist할 때 insert되는 것 아님
            System.out.println("== BEFORE ==");
            em.persist(member);
            System.out.println("== AFTER ==");

            // 트랜잭션 commit할 때 실제로 insert됨
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
