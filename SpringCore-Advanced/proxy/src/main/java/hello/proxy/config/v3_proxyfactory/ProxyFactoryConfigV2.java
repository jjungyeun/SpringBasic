package hello.proxy.config.v3_proxyfactory;

import hello.proxy.app.v2.*;
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
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerV2 orderController(LogTrace logTrace) {
        OrderControllerV2 controller = new OrderControllerV2(orderService(logTrace));
        ProxyFactory factory = new ProxyFactory(controller);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderControllerV2) factory.getProxy();
    }

    @Bean
    public OrderServiceV2 orderService(LogTrace logTrace) {
        OrderServiceV2 service = new OrderServiceV2(orderRepository(logTrace));
        ProxyFactory factory = new ProxyFactory(service);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderServiceV2) factory.getProxy();
    }

    @Bean
    public OrderRepositoryV2 orderRepository(LogTrace logTrace) {
        OrderRepositoryV2 repository = new OrderRepositoryV2();
        ProxyFactory factory = new ProxyFactory(repository);
        factory.addAdvisor(getAdvisor(logTrace));
        return (OrderRepositoryV2) factory.getProxy();
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
