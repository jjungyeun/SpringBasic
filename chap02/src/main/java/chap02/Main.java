package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	public static void main(String[] args) {
		
		// AnnotationConfigApplicationContext 클래스는 자바 설정에서 정보를 읽어와서 빈 객체를 생성하고 관리한다.
		// AppContext에서 정의한 @Bean 설정 정보를 읽어와서 Greeter 객체를 생성하고 초기화한다.
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
		
		// getBean() 메서드는 AnnotationConfigApplicationContext가 자바 설정을 읽어와 생성한 빈 객체를 검색할 때 사용됨
		// 첫번째 파라미터는 빈 객체의 이름(greeter), 두번째 파라미터는 검색할 빈 객체의 타입(Greeter)
		Greeter g = ctx.getBean("greeter", Greeter.class);
		
		// 스프링, 안녕하세요!
		String msg = g.greet("스프링");
		System.out.println(msg);
		
		ctx.close();
	}
}
