package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    public void dynamicA() {
        AInterface targetA = new AImpl();
        TimeInvocationHandler handlerA = new TimeInvocationHandler(targetA);
        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),  // 클래스 로더 정보
                new Class[]{AInterface.class},      // 어떤 인터페이스를 기반으로 프록시를 생성할지 (인터페이스이므로 여러개 가능)
                handlerA);                          // 핸들러 로직 정보
        proxy.call();
        log.info("targetClass={}", targetA.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }

    @Test
    public void dynamicB() {
        BInterface targetB = new BImpl();
        TimeInvocationHandler handlerA = new TimeInvocationHandler(targetB);
        BInterface proxy = (BInterface) Proxy.newProxyInstance(
                BInterface.class.getClassLoader(),  // 클래스 로더 정보
                new Class[]{BInterface.class},      // 어떤 인터페이스를 기반으로 프록시를 생성할지 (인터페이스이므로 여러개 가능)
                handlerA);                          // 핸들러 로직 정보
        proxy.call();
        log.info("targetClass={}", targetB.getClass());
        log.info("proxyClass={}", proxy.getClass());
    }
}
