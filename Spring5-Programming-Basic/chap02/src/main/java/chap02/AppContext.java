package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration	// 해당 클래스를 스프링 설정 클래스로 지정하는 애노테이션
public class AppContext {
	
	// Bean 객체: 스프링이 생성하는 객체
	// @Bean: 해당 메서드가 생성한 객체를 스프링이 관리하는 빈 객체로 등록
	@Bean
	public Greeter greeter() {
		Greeter g = new Greeter();
		g.setFormat("%s, 안녕하세요!");
		return g;
	}
}
