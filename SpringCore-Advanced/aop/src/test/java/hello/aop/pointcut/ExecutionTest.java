package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ExecutionTest {

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
        // helloMethod와 정확하게 일치하는 포인트컷 표현식
        pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void allMatch() {
        // 모든 메서드를 포함하는 포인트컷 표현식
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void nameMatch1() {
        pointcut.setExpression("execution(* hel*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void nameMatch2() {
        pointcut.setExpression("execution(* *el*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void nameMatchFalse() {
        pointcut.setExpression("execution(* nomatch*(..))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void packageMatch() {
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void packageMatch1() {
        // hello.aop.member 패키지의 클래스의 메서드
        pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void packageMatchFalse() {
        // hello.aop 패키지의 클래스의 메서드 -> 하위 패키지 포함 안됨
        pointcut.setExpression("execution(* hello.aop.*.*(..))");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void packageMatchSubPackage() {
        // hello.aop.member 패키지 및 하위 패키지의 클래스의 메서드
        pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void packageMatchSubPackage2() {
        // hello.aop 패키지 및 하위 패키지의 클래스의 메서드
        pointcut.setExpression("execution(* hello.aop..*.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void typeExactMatch() {
        // 타입(클래스) 정확히 일치
        pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void typeMatchSuperType() {
        // 부모타입(여기서는 인터페이스) 선언 -> 부모타입을 표현식에 선언해도 자식타입이 매칭됨
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {
        // 부모타입으로 매칭하려는 경우 부모타입에 있는 메서드만 가능
        pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
        Assertions.assertFalse(pointcut.matches(internalMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsMatch() {
        // String 타입의 파라미터 허용
        pointcut.setExpression("execution(* *(String))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsMatchNoArgs() {
        // 파라미터가 없는 경우 매칭 -> 파라미터가 있으므로 false
        pointcut.setExpression("execution(* *())");
        Assertions.assertFalse(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsMatchStar() {
        // 타입과 관계 없이 한개의 파라미터만 있는 경우에 매칭
        pointcut.setExpression("execution(* *(*))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsMatchAll() {
        // 파라미터의 타입과 개수와 관계없이 모두 혀용
        pointcut.setExpression("execution(* *(..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsMatchStartWithString() {
        // 첫 파라미터가 String이며 이후는 상관 없는 경우와 매칭
        pointcut.setExpression("execution(* *(String, ..))");
        Assertions.assertTrue(pointcut.matches(helloMethod, MemberServiceImpl.class));
    }

}
