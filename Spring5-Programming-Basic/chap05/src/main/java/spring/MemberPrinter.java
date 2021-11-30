package spring;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

public class MemberPrinter {
	
	private DateTimeFormatter dateTimeFormatter;
	
	public void print(Member member) {
		if (dateTimeFormatter == null) {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), member.getRegisterDateTime()
					);
		}
		else {
			System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", 
					member.getId(), member.getEmail(), member.getName(), dateTimeFormatter.format(member.getRegisterDateTime())
					);
		}
	}

//	// DateTimeFormatter 타입의 빈 객체가 등록되어 있지 않으면 에러 발생
//	// 그러면 print()에서 if문의 조건을 만족할 수가 없음.
//	@Autowired
//	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
//		this.dateTimeFormatter = dateTimeFormatter;
//	}
	
	// --- 자동 주입할 대상이 필수가 아닌 경우 ---
	
	// 1. required 속성을 false로 지정
	// 매칭되는 빈이 없어도 Exception이 발생하지 않고 자동 주입을 수행하지 않는다.
	// 이 경우에는 빈이 없으면 setter 메서드를 실행하지 않는다.
//	@Autowired(required = false)
//	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
//		this.dateTimeFormatter = dateTimeFormatter;
//	}
	
	// 2. Optional 속성 (Spring 5 이상)
	// 자동 주입 대상이 Optional일 때, 일치하는 빈이 존재하지 않으면 값이 없는 Optional을,
	// 일치하는 빈이 존재하면 해당 빈을 값으로 갖는 Optional을 인자로 전달한다.
//	@Autowired
//	public void setDateTimeFormatter(Optional<DateTimeFormatter> formatterOpt) {
//		// DateTimeFormatter 빈을 값으로 갖는 Optional을 전달 받은 경우 해당 빈을 필드에 할당 
//		if (formatterOpt.isPresent()) {
//			this.dateTimeFormatter = formatterOpt.get();
//		}
//		// 값이 없는 Optional을 전달 받은 경우 null값을 필드에 할당
//		else {
//			this.dateTimeFormatter = null;
//		}
//	}
	
	// 3. @Nullable 애노테이션 사용
	// 의존 주입 대상 파라미터에 @Nullable 애노테이션을 붙이면, 스프링 컨테이너가 setter 메서드를 호출할 때
	// 자동 주입할 빈이 존재하면 해당 빈을 파라미터로 전달하고, 존재하지 않으면 null을 파라미터로 전달한다.
	// 즉, required 속성을 사용하는 경우와 다르게, setter 메서드가 호출된다.
	@Autowired
	public void setDateTimeFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
	
}
