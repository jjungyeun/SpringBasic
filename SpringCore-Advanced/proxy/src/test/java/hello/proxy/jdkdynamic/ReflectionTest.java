package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    public void reflection0() {
        Hello target = new Hello();

        // 공통 로직1 시작
        log.info("start");
        String result1 = target.callA();
        log.info("result={}", result1);
        // 공통 로직1 종료

        // 공통 로직2 시작
        log.info("start");
        String result2 = target.callB(); // 로직1과 호출하는 메서드만 다름
        log.info("result={}", result2);
        // 공통 로직2 종료
    }

    // 정적인 target.callA(), target.callB()라는 코드를 리플랙션을 통해서 Method로 추상화 했다
    @Test
    public void reflection1() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        log.info("start");
        Object result1 = methodCallA.invoke(target); // target 인스턴스에 있는 callA라는 메서드를 호출
        log.info("result1={}", result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        log.info("start");
        Object result2 = methodCallB.invoke(target); // target 인스턴스에 있는 callB라는 메서드를 호출
        log.info("result2={}", result2);
    }

    @Test
    public void reflection2() throws Exception {
        // 클래스 정보
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    // 메서드를 직접 호출하지 않고 Method라는 메타정보를 통해서 호출함으로서 동적으로 수행할 수 있다.
    private static void dynamicCall(Method methodCall, Object target) throws Exception {
        // 공통 로직 시작
        log.info("start");
        Object result = methodCall.invoke(target);
        log.info("result={}", result);
        // 공통 로직 종료
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
