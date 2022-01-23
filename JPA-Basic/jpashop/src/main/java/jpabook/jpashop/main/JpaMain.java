package jpabook.jpashop.main;

import jpabook.jpashop.domain.Book;

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
            Book book = new Book();
            book.setName("JPA Book");
            book.setPrice(15000);
            book.setStockQuantity(5);
            book.setAuthor("김영한");
            book.setIsbn("1234");
            book.setCreatedBy("원정연");
            book.setCreatedDate(LocalDateTime.now());
            book.setLastModifiedBy("원정연");
            book.setLastModifiedDate(LocalDateTime.now());
            em.persist(book);

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
