package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
//        // 탐색할 패키지의 시작 위치 지정
//        basePackages = "hello.core",
        // 수동으로 등록하는 AppConfig, TestConfig 등의 클래스 제외
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
