package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.AppConf1;
import config.AppConf2;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;

public class MainForSpring {
	
	private static ApplicationContext ctx = null;
	
	public static void main(String[] args) throws IOException {
		
		// 설정 클래스를 이용해서 스프링 컨테이너를 생성
		ctx = new AnnotationConfigApplicationContext(AppConf1.class, AppConf2.class);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("환영합니다.");
		while (true) {
			System.out.println("명령어를 입력하세요:");
			
			// console에서 한 줄 입력받음 
			String command = reader.readLine();
			
			if (command.equalsIgnoreCase("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			if (command.startsWith("new ")) {
				// new [이메일] [이름] [비밀번호] [비밀번호 확인]
				processNewCommand(command.split(" "));
				continue;
			} else if (command.startsWith("change ")) {
				// change [이메일] [기존 비밀번호] [새로운 비밀번호] 
				processChangeCommand(command.split(" "));
				continue;
			} else if (command.startsWith("list")) {
				processListCommand();
				continue;
			}
			else if(command.startsWith("info ")) {
				// info [이메일]
				processInfoCommand(command.split(" "));
				continue;
			}
			printHelp();
		}
	}

	private static void processNewCommand(String[] arg) {
		// arg: new [이메일] [이름] [비밀번호] [비밀번호 확인]
		if (arg.length != 5) {
			printHelp();
			return;
		}
		
		// getBean() 메서드를 이용하여 사용할 객체를 구함
		MemberRegisterService regSvc = ctx.getBean("memberRegisterService", MemberRegisterService.class);
		
		RegisterRequest req = new RegisterRequest();
		req.setEmail(arg[1]);
		req.setName(arg[2]);
		req.setPassword(arg[3]);
		req.setConfirmPassword(arg[4]);
		
		if (!req.isPasswordEqualToConfirmPassword()) {
			System.out.println("암호와 확인이 일치하지 않습니다.\n");
			return;
		}
		try {
			regSvc.regist(req);
			System.out.println("등록했습니다.\n");
		} catch (DuplicateMemberException e) {
			System.out.println("이미 존재하는 이메일입니다.\n");
		}
	}

	private static void processChangeCommand(String[] arg) {
		// arg: change [이메일] [기존 비밀번호] [새로운 비밀번호] 
		if (arg.length != 4) {
			printHelp();
			return;
		}
		
		// assembler에서 의존성을 주입받은 객체 사용
		ChangePasswordService changePwdSvc = ctx.getBean("changePasswordService", ChangePasswordService.class);
		
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}

	private static void processListCommand() {
		MemberListPrinter listPrinter = ctx.getBean("listPrinter", MemberListPrinter.class);
		listPrinter.printAll();
		
	}
	
	private static void processInfoCommand(String[] arg) {
		// arg: info [이메일]
		if (arg.length != 2) {
			printHelp();
			return;
		}
		
		try {
			MemberInfoPrinter infoPrinter = ctx.getBean("infoPrinter", MemberInfoPrinter.class);
			infoPrinter.printMemberInfo(arg[1]);
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		}
		
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("< 명령어 사용법 >");
		System.out.println("신규 등록: new [이메일] [이름] [비밀번호] [비밀번호 확인]");
		System.out.println("비밀번호 변경: change [이메일] [기존 비밀번호] [새로운 비밀번호]");
		System.out.println("전체 멤버 조회: list");
		System.out.println("멤버 정보 조회: info [이메일]");
		System.out.println("프로그램 종료: exit\n");
	}
}
