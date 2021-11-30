package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// @Configuration: 스프링 설정 클래스를 의미. 이걸 붙여야 스프링 설정 클래스로 사용 가능.
@Configuration

// @Import: 함께 사용할 설정 클래스를 지정하는 애노테이션.
// 여러개인 경우 {} 안에 나열하면 된다.
// @Import(AppConf2.class)
@Import({AppConf1.class, AppConf2.class})
public class AppConfImport {
	
}
