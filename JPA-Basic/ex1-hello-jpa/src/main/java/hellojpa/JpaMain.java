package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // EMF는 애플리케이션 실행 단위마다 하나만 생성해야 함
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 트랜잭션 단위로 뭔가를 수행할 때는 EM을 생성하여 사용
        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
//            // save Member
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("youngjin");
//            em.persist(member);

//            // find Member
//            Member findedMember = em.find(Member.class, 1L);
//            System.out.println("findedMember.getId() = " + findedMember.getId());
//            System.out.println("findedMember.getName() = " + findedMember.getName());

//            // remove Member
//            Member findedMember = em.find(Member.class, 1L);
//            em.remove(findedMember);

            // update Member
            // 다시 persist 해줄 필요 없음(변경 감지). 자바 컬랙션을 다루는 것과 유사함
            Member findedMember = em.find(Member.class, 1L);
            findedMember.setName("jungyeun");

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
