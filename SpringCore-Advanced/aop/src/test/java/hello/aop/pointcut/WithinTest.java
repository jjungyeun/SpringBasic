package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    public void printMethod() {
        // public java.lang.String hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    public void exactMatch() {
        // MemberServiceImpl 내의 모든 메서드와 매칭
        pointcut.setExpression("within(hello.aop.member.MemberServiceImpl)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void withinStar() {
        // 타입 이름에 Service가 들어가는 클래스의 모든 메서드와 매칭
        pointcut.setExpression("within(hello.aop.member.*Service*)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void withinMatchSubPackage() {
        // hello.aop 패키지 및 하위 패키지의 클래스의 메서드
        pointcut.setExpression("within(hello.aop..*)");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    @DisplayName("타겟의 타입에만 직접 적용된다. (인터페이스 X)")
    public void withinSuperTypeFalse() {
        // hello.aop 패키지 및 하위 패키지의 클래스의 메서드
        pointcut.setExpression("within(hello.aop.member.MemberService)");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

}
