package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import assembler.Assembler;
import spring.ChangePasswordService;
import spring.DuplicateMemberException;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;
import spring.WrongIdPasswordException;

public class MainForAssembler {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
			}
			printHelp();
		}
	}
	
	private static Assembler assembler = new Assembler();

	private static void processNewCommand(String[] arg) {
		// arg: new [이메일] [이름] [비밀번호] [비밀번호 확인]
		if (arg.length != 5) {
			printHelp();
			return;
		}
		
		// assembler에서 의존성을 주입받은 객체 사용
		MemberRegisterService regSvc = assembler.getMemberRegisterService();
		
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
		ChangePasswordService changePwdSvc = assembler.getChangePasswordService();
		
		try {
			changePwdSvc.changePassword(arg[1], arg[2], arg[3]);
			System.out.println("암호를 변경했습니다.\n");
		} catch (MemberNotFoundException e) {
			System.out.println("존재하지 않는 이메일입니다.\n");
		} catch (WrongIdPasswordException e) {
			System.out.println("이메일과 암호가 일치하지 않습니다.\n");
		}
	}

	private static void printHelp() {
		System.out.println();
		System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
		System.out.println("명령어 사용법:");
		System.out.println("new [이메일] [이름] [비밀번호] [비밀번호 확인]");
		System.out.println("change [이메일] [기존 비밀번호] [새로운 비밀번호]");
		System.out.println();
	}
}
