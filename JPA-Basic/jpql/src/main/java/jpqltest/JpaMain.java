package jpqltest;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setAge(26);
            member.setName("WJY");
            em.persist(member);

            // 반환 타입이 명확하면 TypedQuery 클래스 사용
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.name from Member m where m.id = 1", String.class);
            // 반환 타입이 명확하지 않은 경우 Query 클래스 사용
            Query query3 = em.createQuery("select m.name, m.age from Member m");

            // 결과가 하나 이상일 때 getResultList() 사용. 결과 없으면 빈 리스트 반환
            List<Member> members = query1.getResultList();
            // 결과가 단 하나일 때 getSingleList() 사용. 결과 없어도, 2개 이상이어도 exception 발생
            // Spring Data JPA에서 결과 하나만 반환하는 함수는 결과 없으면 null 반환하도록 제공해줌
            String member1 = query2.getSingleResult();

            // 파라미터 바인딩
            TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.name = :name", Member.class);
            query4.setParameter("name", "WJY");
            Member WJY = query4.getSingleResult();
            System.out.println("WJY = " + WJY);


            tx.commit();
        }
        catch (Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }
        emf.close();
    }
}
