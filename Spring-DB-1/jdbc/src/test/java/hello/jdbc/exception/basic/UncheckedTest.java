package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    public void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    public void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThrows(MyUncheckedException.class, service::callThrow);
    }

    /**
     * RuntimeException을 상속받은 예외는 언체크 예외가 된다.
     */
    static class MyUncheckedException extends RuntimeException {
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    /**
     * 언체크 예외는 예외를 잡아서 처리하거나, 던지지 않아도 된다.
     * 예외를 잡지 않으면 자동으로 밖으로 던진다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리하면 된다.
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 예외를 던지는 코드
         * 언체크 예외는 체크 예외와 다르게 throws를 생략할 수 있다.
         */
        public void callThrow() {
            repository.call();
        }
    }

    static class Repository {
        public void call() {
            // 언체크 예외이기 때문에 잡아서 처리하지 않아도, throws를 선언하지 않아도 된다
            throw new MyUncheckedException("ex");
        }
    }
}
