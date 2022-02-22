package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Jpashop2Application {

    public static void main(String[] args) {
        SpringApplication.run(Jpashop2Application.class, args);
    }

//    // 엔티티를 API에 노출하는 경우에 필요
//    @Bean
//    Hibernate5Module hibernate5Module(){
//        return new Hibernate5Module();
//    }

}