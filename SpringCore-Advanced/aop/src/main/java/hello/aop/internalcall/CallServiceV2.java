package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV2 {

    //    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(
//            ApplicationContext applicationContext,
            ObjectProvider<CallServiceV2> callServiceProvider) {
//        this.applicationContext = applicationContext;
        this.callServiceProvider = callServiceProvider;
    }

    // 빈을 지연 조회하는 방식은 순환 참조가 아님 (자기 자신을 주입 받는 것이 아니기 때문)
    public void external() {
        log.info("call external");
//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal(); // 외부 메서드 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
