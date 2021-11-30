package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;

@Configuration
public class AppConf2 {
	
	@Autowired
	private MemberSummaryPrinter memberPrinter2;
	
	@Bean
	public MemberRegisterService memberRegisterService() {
		// memberDao()가 생성한 객체를 주입 
		return new MemberRegisterService();
	}
	
	@Bean
	public ChangePasswordService changePasswordService() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		
		// setter 메서드에 @Autowired 애노테이션을 붙이면 스프링은 타입이 일치하는 빈 객체를 찾아서 주입한다.
		
//		// memberDao()가 생성한 객체를 주입 
//		pwdSvc.setMemberDao(memberDao);
		return pwdSvc;
	}
	
	
	@Bean
	public MemberListPrinter listPrinter() {
		return new MemberListPrinter();
	}
	
	@Bean
	@Qualifier
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
		memberInfoPrinter.setPrinter(memberPrinter2);
		return memberInfoPrinter;
	}
	
}
