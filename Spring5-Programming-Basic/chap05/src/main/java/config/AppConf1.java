package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;
import spring.MemberPrinter;
import spring.MemberSummaryPrinter;

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
	// @Qualifier 애노테이션을 통해 빈의 이름을 설정할 수 있다. 
	// 이름을 지정하지 않는다면 기본 한정사인 메서드 이름이 빈의 이름이 된다.
	// 이 빈을 자동주입하려면 빈을 주입할 곳에 동일한 애노테이션을 붙이면 된다.
	@Qualifier("printer")
	public MemberPrinter memberPrinter1() {
		return new MemberPrinter();
	}
	
	// MemberSummaryPrinter는 MemberPrinter 클래스를 상속받는다.
	// 그렇기 때문에 어딘가에서 빈 객체를 사용하기 위해 MemberPrinter 타입의 객체를 자동 주입한다면,
	// 두 타입의 클래스 모두 주입 가능하므로 MemberPrinter 타입의 빈을 한정할 수 없다는 오류가 발생한다.
	// 따라서 @Qualifier를 통해 한정사를 부여하거나, MemberSummaryPrinter를 사용하는 곳에서 명확하게 타입을 지정해야한다.
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
		
	}
}
