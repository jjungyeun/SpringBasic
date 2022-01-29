package jpqltest;

import jpqltest.dto.MemberDto;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member0 = new Member();
            member0.setAge(26);
            member0.setName("member1");
            em.persist(member0);

            Team team = new Team();
            team.setName("team1");
            team.addMember(member0);
            em.persist(team);

            Member member2 = new Member();
            member2.setAge(24);
            member2.setName("member2");
            em.persist(member2);

            Team team2 = new Team();
            team2.setName("team2");
            team2.addMember(member2);
            em.persist(team2);

            em.flush();
            em.clear();

            // 반환 타입이 명확하면 TypedQuery 클래스 사용
            TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("select m.name from Member m where m.name = 'member1'", String.class);
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

            // 프로젝션 -  여러 값 조회 시 new 명령어로 조회하는 방법
            List<MemberDto> resultList = em.createQuery("select new jpqltest.dto.MemberDto(m.name, m.age) from Member m", MemberDto.class)
                    .getResultList();
            for (MemberDto memberDto : resultList) {
                System.out.println("memberDto = " + memberDto);
            }

            // 페이징
            List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(5)
                    .getResultList();
            System.out.println("result.size() = " + result.size());
            for (Member member : result) {
                System.out.println(member);
            }

            // 조인
            String jpql = "select m from Member m inner join m.team t";
            String jpql2 = "select m from Member m left join m.team t";
            String jpql3 = "select m, t from Member m, Team t where m.name = t.name";
            // 연관관계 없는 외부 조인 (멤버 이름 = 팀 이름)인 멤버, 팀 쌍 조회
            String jpql4 = "select m, t from Member m left join Team t on m.name = t.name";
            List<Object[]> resultList2 = em.createQuery(jpql4, Object[].class)
                    .getResultList();
            for (Object[] objects : resultList2) {
                System.out.println("objects[0] = " + (Member)objects[0]);
                System.out.println("objects[1] = " + (Team)objects[1]);
            }

            // 사용자 정의 함수 사용
            String jpql5 = "select function('group_concat', m.name) from Member m";
            List<String> concatResult = em.createQuery(jpql5, String.class)
                    .getResultList();
            for (String s : concatResult) {
                System.out.println("s = " + s);
            }


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
