package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v1.*;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 controller = new OrderControllerV1Impl(orderService(logTrace));
        ProxyFactory factory = new ProxyFactory(controller);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderControllerV1) factory.getProxy();
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 service = new OrderServiceV1Impl(orderRepository(logTrace));
        ProxyFactory factory = new ProxyFactory(service);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderServiceV1) factory.getProxy();
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 repository = new OrderRepositoryV1Impl();
        ProxyFactory factory = new ProxyFactory(repository);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderRepositoryV1) factory.getProxy();
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        // Pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        // Advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
