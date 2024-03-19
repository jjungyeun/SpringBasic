package hello.aop.internalcall;

import hello.aop.internalcall.aop.CallLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callService;

    @Test
    public void external() {
        log.info("target={}", callService.getClass());
        // external()에는 aspect가 적용됨
        // 내부에서 호출되는 internal()에는 aspect 적용 안됨
        callService.external();
    }

    @Test
    public void internal() {
        // aspect 적용됨
        callService.internal();
    }

}