package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Jpashop2Application {

    public static void main(String[] args) {
        SpringApplication.run(Jpashop2Application.class, args);
    }

    // 엔티티를 API에 노출하는 경우에 필요
    @Bean
    Hibernate5Module hibernate5Module(){
        return new Hibernate5Module();
    }

}