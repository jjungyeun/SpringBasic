package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;

// @Configuration: 스프링 설정 클래스를 의미. 이걸 붙여야 스프링 설정 클래스로 사용 가능.
@Configuration

// @Component 애노테이션을 붙인 클래스를 스캔해서 빈으로 등록하도록 하는 설정
// basePackages 속성은 스캔 대상 패키지 목록을 지정한다. 해당 패키지와 그 하위 패키지에 속한 클래스를 스캔 대상으로 설정한다.
// 스캔 대상 중 @Component 애노테이션이 붙은 클래스의 객체를 생성해서 빈으로 등록한다.
@ComponentScan(basePackages = {"spring"})
public class AppCtx {
		
		@Bean
		@Qualifier("printer")
		public MemberPrinter memberPrinter1() {
			return new MemberPrinter();
		}
		
		@Bean
		@Qualifier("summaryPrinter")
		public MemberSummaryPrinter memberPrinter2() {
			return new MemberSummaryPrinter();
			
		}
}
