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
            member.setId(3L);
            member.setUserName("dongyeun");

            // 영속 상태 -> persist할 때 insert되는 것 아님
            System.out.println("== BEFORE ==");
            em.persist(member);
            System.out.println("== AFTER ==");

            // 1차 캐시에서 가져오기 때문에 select 쿼리가 실행되지 않음
            Member cacheMember = em.find(Member.class, 3L);
            System.out.println("cacheMember = " + cacheMember);

            // 1차 캐시에 존재하지 않는 객체는 DB에 select 쿼리를 날려서 가져옴
            Member dbMember = em.find(Member.class, 1L);
            System.out.println("dbMember = " + dbMember);

            // 한번 더 가져올 때는 1차 캐시에서 가져옴
            Member dbMember2 = em.find(Member.class, 1L);
            System.out.println("dbMember2 = " + dbMember2);

            // 한 트랜잭션 내에서는 영속 엔티티의 동일성 보장
            System.out.println("result = " + (dbMember == dbMember2));  // true

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
