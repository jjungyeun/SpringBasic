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
            Member member1 = new Member();
            member1.setAge(26);
            member1.setName("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setAge(24);
            member2.setName("member2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setAge(25);
            member3.setName("member3");
            em.persist(member3);

            Member member4 = new Member();
            member4.setAge(27);
            member4.setName("member4");
            em.persist(member4);

            Team team = new Team();
            team.setName("team1");
            team.addMember(member1);
            team.addMember(member2);
            em.persist(team);

            Team team2 = new Team();
            team2.setName("team2");
            team2.addMember(member3);
            em.persist(team2);

            Team team3 = new Team();
            team3.setName("team3");
            em.persist(team3);

//            em.flush();
//            em.clear();

            //== query ==//
//            String query = "select m from Member m";
//            List<Member> result = em.createQuery(query, Member.class).getResultList();
//
//            for (Member member : result) {
//                System.out.println("member = " + member.getName() + ", " + member.getTeam().getName());
//                // member1, team1 (SQL)
//                // member2, team1 (1차 캐시)
//                // member3, team2 (SQL)
//                // member4, null (필요 객체 없음)
//
//                // 회원 100명 -> 최악의 경우 쿼리 100개 추가. (N+1 쿼리 문제)
//                // 지연로딩을 하더라도 추가 쿼리가 생기지 않는 게 아님
//                // => fetch join 사용
//            }

//            // fetch join
//            String fetchQuery = "select m from Member m join fetch m.team";
//            List<Member> fetchResult = em.createQuery(fetchQuery, Member.class).getResultList();
//            for (Member member : fetchResult) {
//                System.out.println("member = " + member.getName() + ", " + member.getTeam().getName());
//                // fetch join으로 이미 한번에 다 실제 객체를 불러옴
//                // 지연로딩 설정 돼있어도 fetch join이 우선임
//            }

//            // fetch join - collection
////            String fetchCollectionQuery = "select t from Team t join fetch t.members where t.name = 'team1'";
//            String fetchCollectionQuery = "select distinct t from Team t join fetch t.members where t.name = 'team1'";
//            List<Team> fetchCollectionResult = em.createQuery(fetchCollectionQuery, Team.class).getResultList();
//            for (Team t : fetchCollectionResult) {
//                System.out.println("team = " + t.getName() + ", " + t.getMembers());
//                // team1에 속한 멤버가 2명이기 때문에 team1 객체가 결과(fetchCollectionResult)에 두번 들어감
//                // distinct 넣으면 애플리케이션 레벨에서 엔티티 중복도 처리해줌!
//            }

//            // Named Query
//            List<Member> namedMember = em.createNamedQuery("Member.findByName", Member.class)
//                    .setParameter("name", "member1")
//                    .getResultList();
//            for (Member member : namedMember) {
//                System.out.println("member = " + member);
//            }

            // Bulk Operation
            // 쿼리 한번에 관련 객체 정보 업데이트
            // 영속성 컨텍스트에는 수정 안되므로 영속성 컨텍스트 초기화해주어야 함
            String bulkQuery = "update Member m set m.age = 20";
            int resultCnt = em.createQuery(bulkQuery)
                    .executeUpdate();
            System.out.println("resultCnt = " + resultCnt); // 변경된 row 개수 반환

            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("Before Clear: findMember.getAge() = " + findMember.getAge());   // != 20 (1차 캐시에는 반영 안됨)

            em.clear();
            findMember = em.find(Member.class, member1.getId());
            System.out.println("After Clear: findMember.getAge() = " + findMember.getAge());   // == 20 (DB에서 다시 불러옴)


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
