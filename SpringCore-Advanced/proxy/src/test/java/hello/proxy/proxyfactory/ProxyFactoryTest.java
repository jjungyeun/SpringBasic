package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    public void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.save();

        // ProxyFactory로 만들어진 프록시는 AopUtils로 정보를 확인할 수 있음
        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertTrue(AopUtils.isJdkDynamicProxy(proxy));
        Assertions.assertFalse(AopUtils.isCglibProxy(proxy));
    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    public void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.call();

        // ProxyFactory로 만들어진 프록시는 AopUtils로 정보를 확인할 수 있음
        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertTrue(AopUtils.isCglibProxy(proxy));
        Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    public void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // 항상 CGLIB 사용
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.save();

        // ProxyFactory로 만들어진 프록시는 AopUtils로 정보를 확인할 수 있음
        Assertions.assertTrue(AopUtils.isAopProxy(proxy));
        Assertions.assertTrue(AopUtils.isCglibProxy(proxy));
        Assertions.assertFalse(AopUtils.isJdkDynamicProxy(proxy));
    }
}
