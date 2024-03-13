package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

@Slf4j
public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    public void args() {
        Assertions.assertTrue(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertFalse(pointcut("args()")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(..)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(*)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(String, ..)")
                .matches(helloMethod, MemberServiceImpl.class));
    }

    @Test
    public void argsVsExecution() {
        /**
         * execution은 파라미터 타입이 정확하게 일치해야 하지만, args는 부모타입도 가능하다.
         * execution: 메서드의 시그니처로 판단 (정적인 정보)
         * args:      런타임에 전달된 인수의 인스턴스로 판단 (동적인 정보)
         */

        // args
        Assertions.assertTrue(pointcut("args(String)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(java.io.Serializable)")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertTrue(pointcut("args(Object)")
                .matches(helloMethod, MemberServiceImpl.class));

        // execution
        Assertions.assertTrue(pointcut("execution(* *(String))")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertFalse(pointcut("execution(* *(java.io.Serializable))")
                .matches(helloMethod, MemberServiceImpl.class));
        Assertions.assertFalse(pointcut("execution(* *(Object))")
                .matches(helloMethod, MemberServiceImpl.class));
    }

}
