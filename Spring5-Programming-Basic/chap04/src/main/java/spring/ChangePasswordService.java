package spring;

import org.springframework.beans.factory.annotation.Autowired;

public class ChangePasswordService {
	
	private MemberDao memberDao;
	
	public void changePassword(String email, String oldPassword, String newPassword) {
		Member member = memberDao.selectByEmail(email);
		
		if(member == null)
			throw new MemberNotFoundException();
		
		member.changePassword(oldPassword, newPassword);
		
		memberDao.update(member);
	}
	
	// setter 메서드에 @Autowired를 붙이면 파라미터의 타입에 맞는 객체를 자동으로 주입해준다. 따라서 setter를 별도로 호출할 필요가 없다.
	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
