package spring;

import org.springframework.beans.factory.annotation.Autowired;

public class MemberInfoPrinter {
	
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberPrinter memberPrinter;
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) 
			throw new MemberNotFoundException();
		memberPrinter.print(member);
		System.out.println();
	}
	
}
