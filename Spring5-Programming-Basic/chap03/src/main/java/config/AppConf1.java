package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;
import spring.MemberPrinter;

// @Configuration: 스프링 설정 클래스를 의미. 이걸 붙여야 스프링 설정 클래스로 사용 가능.
@Configuration
public class AppConf1 {
	
	// @Bean: 해당 메서드가 생성한 객체를 스프링 빈이라고 설정. 
	// 각 메서드마다 한 개의 빈 객체를 생성하며, 메서드 이름을 빈 객체의 이름으로 사용.
	@Bean
	public MemberDao memberDao() {
		return new MemberDao();
	}
	
	@Bean
	public MemberPrinter memberPrinter() {
		return new MemberPrinter();
	}
	
}
