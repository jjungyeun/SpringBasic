package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("infoPrinter")
public class MemberInfoPrinter {
	
	@Autowired
	private MemberDao memberDao;
	
	private MemberPrinter memberPrinter;
	
	
	public void printMemberInfo(String email) {
		Member member = memberDao.selectByEmail(email);
		if (member == null) 
			throw new MemberNotFoundException();
		memberPrinter.print(member);
		System.out.println();
	}


	@Autowired
	public void setPrinter(MemberSummaryPrinter memberPrinter2) {
		this.memberPrinter = memberPrinter2;
	}
	
}
