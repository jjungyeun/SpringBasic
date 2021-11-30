package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;

@Configuration
public class AppConf2 {
	
	// @Autowired: 스프링의 자동 주입 기능을 위한 애노테이션.
	// 해당 타입의 빈을 찾아서 필드에 할당한다.
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberPrinter memberPrinter;
	
	@Bean
	public MemberRegisterService memberRegisterService() {
		// memberDao()가 생성한 객체를 주입 
		return new MemberRegisterService(memberDao);
	}
	
	@Bean
	public ChangePasswordService changePasswordService() {
		ChangePasswordService pwdSvc = new ChangePasswordService();
		// memberDao()가 생성한 객체를 주입 
		pwdSvc.setMemberDao(memberDao);
		return pwdSvc;
	}
	
	
	@Bean
	public MemberListPrinter listPrinter() {
		// memberDao(), memberPrinter()가 생성한 객체를 주입 
		return new MemberListPrinter(memberDao, memberPrinter);
	}
	
	@Bean
	public MemberInfoPrinter infoPrinter() {
		MemberInfoPrinter memberInfoPrinter = new MemberInfoPrinter();
		
		// ** MemberInfoPrinter 클래스에서 @Autowired를 이용해 객체를 주입했으므로 setter 필요 없어짐 **
		
//		// setter를 이용하여 memberDao 빈, memberPrinter 빈 주입
//		memberInfoPrinter.setMemberDao(memberDao);
//		memberInfoPrinter.setMemberPrinter(memberPrinter);
		
		return memberInfoPrinter;
	}
	
}
